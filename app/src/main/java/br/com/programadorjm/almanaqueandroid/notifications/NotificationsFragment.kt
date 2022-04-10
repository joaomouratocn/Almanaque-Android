package br.com.programadorjm.almanaqueandroid.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.fragment.app.Fragment
import br.com.programadorjm.almanaqueandroid.MainActivity
import br.com.programadorjm.almanaqueandroid.R
import br.com.programadorjm.almanaqueandroid.databinding.FragmentNotificationsBinding

private const val NOTIFICATION_ID = 1
private const val CHANNEL_ID = "CHANNEL_01" //id unico para o canal de notificação
private const val NOTIFICATION_UPDATE = "UPDATE"
private const val NOTIFICATION_CANCEL = "CANCEL"
private const val NOTIFICATION_DELETE_ALL = "DELETE_ALL"

class Notifications : Fragment() {
    private val notificationReceiver = NotificationReceiver()
    private lateinit var binding: FragmentNotificationsBinding
    private lateinit var notificationManager: NotificationManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentNotificationsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        registerNotificationReceiver()
        createNotificationChannel()
        setButtonStatus(buttonSend = true)

        binding.btnSendNotification.setOnClickListener { sendNotification() }
        binding.btnUpdateNotification.setOnClickListener { updateNotification() }
        binding.btnDeleteNotification.setOnClickListener { cancelNotification() }
    }

    private fun sendNotification() {
        //Intenção do click na notificação
        val intent = Intent(requireContext(), MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent = PendingIntent.getActivity(requireContext(), NOTIFICATION_ID, intent, 0)

        //Intenção no click no botão update na notificação
        val updateAction = Intent(NOTIFICATION_UPDATE)
        val actionUpdate = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            updateAction,
            PendingIntent.FLAG_ONE_SHOT
        )

        //Intenção no click do botão cancel
        val cancelAction = Intent(NOTIFICATION_CANCEL)
        val actionCancel = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            cancelAction,
            PendingIntent.FLAG_ONE_SHOT
        )

        //Intenção limpar tudo do sistema
        val deleteAllAction = Intent(NOTIFICATION_DELETE_ALL)
        val actionDeleteAll = PendingIntent.getBroadcast(
            requireContext(),
            NOTIFICATION_ID,
            deleteAllAction,
            PendingIntent.FLAG_ONE_SHOT
        )


        val builder = getNotificationBuilder(R.string.str_my_test_notification, R.string.str_sample_text_notification)
        builder.setContentIntent(pendingIntent) // setando click na notificação
        builder.addAction(R.drawable.ic_baseline_android_24, "Update", actionUpdate)
        builder.addAction(R.drawable.ic_baseline_android_24, "Cancel", actionCancel)
        builder.setDeleteIntent(actionDeleteAll)
        createNotification(builder)
        setButtonStatus(buttonUpdate = true, buttonCancel = true)
    }

    private fun updateNotification(){
        val intent = Intent(requireContext(), MainActivity::class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent:PendingIntent = PendingIntent.getActivity(requireContext(), NOTIFICATION_ID, intent, 0)
        val builder = getNotificationBuilder(R.string.str_another_notification, R.string.str_sample_another_notification)

        builder.setContentIntent(pendingIntent)
        createNotification(builder)
        setButtonStatus(buttonCancel = true)

    }

    private fun cancelNotification() {
        with(NotificationManagerCompat.from(requireContext())){
            cancel(NOTIFICATION_ID)
        }
        setButtonStatus(buttonSend = true)
    }

    private fun createNotification(builder: NotificationCompat.Builder) {
        with(NotificationManagerCompat.from(requireContext())){
            notify(NOTIFICATION_ID, builder.build())
        }
    }

    private fun getNotificationBuilder(@StringRes titleNotification:Int, @StringRes textNotification:Int) =
        NotificationCompat.Builder(requireContext(), CHANNEL_ID)
        .setSmallIcon(R.drawable.ic_baseline_android_24)
        .setContentTitle(getString(titleNotification))
        .setContentText(getString(textNotification))
        .setPriority(NotificationCompat.PRIORITY_HIGH)
        .setAutoCancel(true)

    private fun createNotificationChannel(){
        // if para suporte a api 26+
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val name = getString(R.string.str_channel_name) //Define o nome do tipo da notificação, para controle do usuário nas config do app
            val descriptionText = getString(R.string.str_channel_description)
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply { description = descriptionText }

            //registrando notificação no sistema
            notificationManager =
                requireActivity().getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    //passando as intents para o broadcast
    private fun registerNotificationReceiver(){
        val notificationFilters = IntentFilter()
        notificationFilters.addAction(NOTIFICATION_UPDATE)
        notificationFilters.addAction(NOTIFICATION_CANCEL)
        notificationFilters.addAction(NOTIFICATION_DELETE_ALL)
        requireActivity().registerReceiver(notificationReceiver, notificationFilters)
    }

    //Estanciando broadcastreceiver localmente
    inner class NotificationReceiver:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent) {
            when(intent.action){
                NOTIFICATION_UPDATE -> {
                    Toast.makeText(requireContext(), "Update", Toast.LENGTH_SHORT).show()
                }
                NOTIFICATION_CANCEL -> cancelNotification()
                NOTIFICATION_DELETE_ALL -> setButtonStatus(buttonSend = true)
            }
        }
    }

    //Ultilitario para ativar e desativar botoes na tela
    private fun setButtonStatus(
        buttonSend:Boolean = false,
        buttonUpdate:Boolean = false,
        buttonCancel:Boolean = false,
    ){
        binding.btnSendNotification.isEnabled = buttonSend
        binding.btnUpdateNotification.isEnabled = buttonUpdate
        binding.btnDeleteNotification.isEnabled = buttonCancel
    }
}
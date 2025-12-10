package com.euphoria.selfcare.euphoria

import android.app.AlarmManager
import android.app.PendingIntent
import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.euphoria.selfcare.euphoria.databinding.FragmentNotificationBinding
import java.util.*

class NotificationFragment : Fragment() {

    private var _binding: FragmentNotificationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentNotificationBinding.inflate(inflater, container, false)
        val view = binding.root

        val alarmManager = requireContext().getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(requireContext(), ReminderReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            requireContext(),
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )

        // 🔥 Android 12+ harus cek izin exact alarm
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                val intentPerm = Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM)
                intentPerm.data = android.net.Uri.parse("package:${requireActivity().packageName}")
                startActivity(intentPerm)
                Toast.makeText(requireContext(), "Izinkan alarm untuk bekerja", Toast.LENGTH_LONG).show()
                return view
            }
        }

        // ⭐ PILIH WAKTU NOTIFIKASI
        binding.btnPickTime.setOnClickListener {

            val calendar = Calendar.getInstance()
            val hour = calendar.get(Calendar.HOUR_OF_DAY)
            val minute = calendar.get(Calendar.MINUTE)

            val timePicker = TimePickerDialog(
                requireContext(),
                { _, selectedHour, selectedMinute ->
                    val selectedCal = Calendar.getInstance().apply {
                        set(Calendar.HOUR_OF_DAY, selectedHour)
                        set(Calendar.MINUTE, selectedMinute)
                        set(Calendar.SECOND, 0)
                        set(Calendar.MILLISECOND, 0)

                        // Jika waktu sudah lewat → set untuk besok
                        if (before(Calendar.getInstance())) {
                            add(Calendar.DAY_OF_MONTH, 1)
                        }
                    }

                    alarmManager.setExact(
                        AlarmManager.RTC_WAKEUP,
                        selectedCal.timeInMillis,
                        pendingIntent
                    )

                    Toast.makeText(
                        requireContext(),
                        "🔔 Pengingat diset pada ${selectedHour}:${"%02d".format(selectedMinute)}",
                        Toast.LENGTH_LONG
                    ).show()
                },
                hour,
                minute,
                true
            )

            timePicker.show()
        }

        // Batalkan alarm
        binding.btnCancelReminder.setOnClickListener {
            alarmManager.cancel(pendingIntent)
            Toast.makeText(requireContext(), "❌ Pengingat dibatalkan!", Toast.LENGTH_SHORT).show()
        }

        return view
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

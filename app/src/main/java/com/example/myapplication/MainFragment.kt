package com.example.myapplication

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.SystemClock
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.myapplication.databinding.FragmentMainBinding
import java.util.concurrent.TimeUnit


class MainFragment : Fragment(R.layout.fragment_main) {
    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    private var alarmManager: AlarmManager? = null

    private var globalTime: Long = 0
    private var timeForNotification: Int = 0

    private var header: String? = null
    private var body: String? = null
    private var details: String? = null

    private var isCheckBoxChecked = false

    private var isHeaderEmpty = true
    private var isBodyEmpty = true
    private var isTimeEmpty = true

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentMainBinding.bind(view)

        with(binding){
            etDetails.isEnabled = false
            button1.isEnabled = false

            checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                etDetails.isEnabled = isChecked
                isCheckBoxChecked = isChecked
            }
            button1.setOnClickListener{
                header = etHeader.editText?.text.toString()
                body = etBody.editText?.text.toString()
                details = if(isCheckBoxChecked) {
                    etDetails.editText?.text.toString()
                } else {
                    null
                }

                timeForNotification = Integer.valueOf(etTime.editText?.text.toString())

                setAlarm(timeForNotification, header, body, details)

            }

            button2.setOnClickListener{
                val timeDiff = if(globalTime != 0L) {
                    (globalTime - SystemClock.elapsedRealtime()) / 1000
                } else { 0 }

                if(timeDiff > 0L) {
                    alarmManager?.cancel(getPendingIntent(header, body, details))
                    Toast.makeText(context, "Notification was cancelled", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context, "Notification wasn't cancelled", Toast.LENGTH_SHORT).show()
                }
            }

            button3.setOnClickListener {
                val timeInMillis = SystemClock.uptimeMillis()
                val time = String.format("%02d min, %02d sec",
                    TimeUnit.MILLISECONDS.toMinutes(timeInMillis),
                    TimeUnit.MILLISECONDS.toSeconds(timeInMillis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(timeInMillis))
                    )

                Toast.makeText(context, time, Toast.LENGTH_SHORT).show()
            }

        }

        initEditTextListeners()
    }

    private fun initEditTextListeners(){
        with(binding){
            etHeader.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    isHeaderEmpty = s.isEmpty()
                }
                override fun afterTextChanged(s: Editable) {
                    checkIsEmpty()
                }
            })

            etBody.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    isBodyEmpty = s.isEmpty()
                }
                override fun afterTextChanged(s: Editable) {
                    checkIsEmpty()
                }
            })

            etTime.editText?.addTextChangedListener(object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    isTimeEmpty = s.isEmpty()
                }
                override fun afterTextChanged(s: Editable) {
                    checkIsEmpty()
                }
            })
        }
    }

    private fun setAlarm(time: Int, header: String?, body: String?, details: String?){
        alarmManager = context?.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        val pendingIntent = getPendingIntent(header, body, details)

        globalTime = SystemClock.elapsedRealtime() + time * 1000

        alarmManager?.set(
            AlarmManager.ELAPSED_REALTIME_WAKEUP,
            globalTime,
            pendingIntent
        )

    }

    private fun checkIsEmpty(){
        binding.button1.isEnabled = !isHeaderEmpty && !isBodyEmpty && !isTimeEmpty
    }

    private fun getPendingIntent(header: String?,body: String?, details: String?) : PendingIntent =
        Intent(context, AlarmReceiver::class.java).apply{
            putExtra(AlarmReceiver.HEADER, header)
            putExtra(AlarmReceiver.BODY, body)
            putExtra(AlarmReceiver.DETAILS, details)
        }.let {
            intent -> PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        }

    companion object{
        const val MAIN_FRAGMENT_TAG = "MAIN_FRAGMENT_TAG"
        fun newInstance(bundle: Bundle) =
            MainFragment().apply {
                arguments = Bundle().apply {
                    putAll(bundle)
                }
            }
    }


}
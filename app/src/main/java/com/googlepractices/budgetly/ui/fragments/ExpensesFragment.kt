package com.googlepractices.budgetly.ui.fragments

import android.annotation.SuppressLint
import android.content.*
import android.os.Bundle
import android.provider.Settings
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.googlepractices.budgetly.NewExpenseActivity
import com.googlepractices.budgetly.R
import com.googlepractices.budgetly.adapter.ExpensesAdapter
import com.googlepractices.budgetly.databinding.ExpenseListRowBinding
import com.googlepractices.budgetly.databinding.FragmentExpensesBinding
import com.googlepractices.budgetly.services.MyNotificationListener
import com.googlepractices.budgetly.ui.BudgetLYActivity
import com.googlepractices.budgetly.util.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.fragment_expenses.*

class ExpensesFragment: Fragment() {
    private val ENABLED_NOTIFICATION_LISTENERS = "enabled_notification_listeners"
    private val ACTION_NOTIFICATION_LISTENER_SETTINGS = "android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS"
    private lateinit var broadcastReceiver: BroadcastReceiver


    private var _binding: FragmentExpensesBinding? = null
    private val binding get() = _binding!!
    private lateinit var itemBindind: ExpenseListRowBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(!isNotificaionServiceEnabled()){
            val enableNotificationListener = buildNotificationServiceAlertDialog()
            enableNotificationListener.show()
        }

        broadcastReceiver = myBroadcastReceiver()
        val intentFilter = IntentFilter().also {
            it.addAction("com.googlepractices.budgetly.services.mynotificationlistener")
        }
        (activity as BudgetLYActivity).registerReceiver(broadcastReceiver, intentFilter)


    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentExpensesBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        add_event_button.setOnClickListener {
            val intent = Intent(view.context, NewExpenseActivity::class.java)
            view.context.startActivity(intent)
        }

        setuprv()
    }

//    sets the adapter, layoutManager and adds a separator line to each itemView in the rv
    private fun setuprv() {
        val adapter = ExpensesAdapter()
        binding.recyclerView.apply {
            this.adapter = adapter
            this.layoutManager = LinearLayoutManager(context)
            this.addItemDecoration(SimpleDividerItemDecoration(context))
        }
    }
/**
 * Is Notification Service Enabled.
 * Verifies if the notification listener service is enabled.
 * */
    fun isNotificaionServiceEnabled(): Boolean{
        var pkgName = Package.getPackages()
        val flat = Settings.Secure.getString(
            context?.contentResolver,
            ENABLED_NOTIFICATION_LISTENERS
        )
        if (!TextUtils.isEmpty(flat)){
            val names = flat.split(":")
            for (name in names){
                val componentName = ComponentName.unflattenFromString(name)
                if (TextUtils.equals(pkgName.toString(), componentName?.packageName)){
                    Log.i("pkgs", "${pkgName.toString()}")
                    return true
                }
            }
        }
        return false
    }

    @SuppressLint("SetTextI18n")
    private fun reactToNotificationReceived(notificationCode: Int, messageString: String){
        when(notificationCode){
            MyNotificationListener.GOOGLEPAY_CODE -> binding.expensesTitle.setText("$messageString, google")
            MyNotificationListener.INSTAGRAM_CODE -> binding.expensesTitle.setText("$messageString, insta")
            MyNotificationListener.YOUTUBE_CODE -> binding.expensesTitle.setText("$messageString, uTube")
        }
    }

    inner class myBroadcastReceiver: BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            val receivedNotificationCode = intent?.getIntExtra("Notification Code", -1)
            val pkgs = intent?.getStringExtra("package")
            val title = intent?.getStringExtra("title")
            val text = intent?.getStringExtra("text")
            if (text != null) {
                val messageString = "$pkgs, $title, $text"
                reactToNotificationReceived(receivedNotificationCode!!, messageString)
            }
        }
    }

    private fun buildNotificationServiceAlertDialog(): AlertDialog{
        val dialog = AlertDialog.Builder(requireContext())
        dialog.apply {
            setTitle("Activate Notification Listener")
            setPositiveButton("yes") { _, _ ->7
                startActivity(Intent(ACTION_NOTIFICATION_LISTENER_SETTINGS))
            }
        }
        return dialog.create()
    }

    override fun onDestroy() {
        (activity as BudgetLYActivity).unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

//    inner class ExpensesAdaper: RecyclerView.Adapter<ExpensesAdaper.ExpensesViewHolder>(){
//        inner class ExpensesViewHolder(binding: ExpenseListRowBinding):
//            RecyclerView.ViewHolder(binding.root)
//
//        private val diffUtil = object : DiffUtil.ItemCallback<Expense>() {
//            override fun areItemsTheSame(oldItem: Expense, newItem: Expense): Boolean {
//                TODO("Not yet implemented")
//            }
//
//            override fun areContentsTheSame(oldItem: Expense, newItem: Expense): Boolean {
//                TODO("Not yet implemented")
//            }
//        }
//
//    }

}
package com.googlepractices.budgetly.ui

import android.content.Intent
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class StartActivity : AppCompatActivity() {

    val nfcAdapter = NfcAdapter.getDefaultAdapter(this)
    var rawMessage: Array<Parcelable>? = null
    lateinit var rawText: NdefMessage
    lateinit var ndefRecord: NdefRecord
    var myIntent: Intent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i("support nfc", "${(nfcAdapter != null)}")
        Log.i("nfc enabled: ", "${(nfcAdapter?.isEnabled)}")

        if (intent != null){
            processIntent(intent)
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)

        if (intent != null){
            processIntent(intent)
        }
    }

    private fun processIntent(checkIntent: Intent){
        //check if intent has the action of a discovered NFC tag
        //with NDEF formatted contents
        //and if the user as NFC support, if true sends start the app main activity with the message received from the nfc tag
        if (nfcAdapter != null) {

            myIntent = Intent(this, BudgetLYActivity::class.java)

            if (checkIntent.action == NfcAdapter.ACTION_NDEF_DISCOVERED) {

                //get the raw NDEF message from the tag in the form of an array
                //access the first message
                //access the fisrt record of the message
                rawMessage = checkIntent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
                rawText = rawMessage?.get(0) as NdefMessage
                ndefRecord = rawText.records[0]


                Log.i("nfc URI detected: ", ndefRecord.payload.contentToString())
                myIntent!!.putExtra("message", ndefRecord.payload.contentToString())
                this.startActivity(intent)
                this.finish()
            }
        } else{
            Toast.makeText(this, "No NFC support", Toast.LENGTH_LONG).show()
            val coroutineScope = CoroutineScope(Dispatchers.Main)
            coroutineScope.launch {
                delay(5000L)
                this@StartActivity.finish()
            }
        }
    }

}


















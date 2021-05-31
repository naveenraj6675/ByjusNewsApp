package com.android.byjusnewapp.views.base

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.ActivityInfo
import android.net.ConnectivityManager
import android.os.Bundle
import android.telephony.TelephonyManager
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Observer
import com.android.byjusnewapp.R
import com.android.byjusnewapp.enums.LoaderStatus
import com.android.byjusnewapp.viewmodel.MyBaseViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*


abstract class MyAppCompatActivity : AppCompatActivity() {
    protected val TAG = this.javaClass.simpleName


    private var mBaseView: ViewGroup? = null
    private var progressShown = false
    private var mLoaderView: View? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN)
        initProgress()
    }

    //Initializing the progress view
    private fun initProgress() {
        mBaseView = this.findViewById(android.R.id.content)
        mLoaderView = View.inflate(this, R.layout.loader, null)

    }


    //To hide Keyboard
    fun hideKeyboard() {
        val inputMethodManager =
            this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager;
        val view = this.currentFocus;
        if (view != null)
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0);
    }

    //To show keyboard
    fun showKeyboard() {
        val inputManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        // check if no view has focus:
        val view = this.currentFocus
        if (view != null)
            inputManager.toggleSoftInputFromWindow(
                view.windowToken,
                InputMethodManager.SHOW_FORCED,
                0
            )
    }

    //To show snackbar
    protected fun showSnackbar(
        message: String?
    ) {
        val snackbarMessage = SpannableStringBuilder(message)
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content), snackbarMessage,
            Snackbar.LENGTH_LONG
        )
        snackbar.setDuration(2000)
        val snackBarView = snackbar.view
        val snackBarBg = R.color.primaryVariant
        val snackBarTextColor = R.color.onError

        snackBarView.setBackgroundColor(ContextCompat.getColor(this, snackBarBg))
        val textView =
            snackBarView.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
        textView.maxLines = 4
        textView.setTextColor(ContextCompat.getColor(this, snackBarTextColor))
        snackbar.show()
    }

    private val defaultDialogClickListener = DialogInterface.OnClickListener { dialog, which ->
        dialog.dismiss()
    }

    //Call this method while setting up Viewmodel to init progress
    protected fun setUpLoader(viewModel: MyBaseViewModel) {
        viewModel.isLoading.observe(this, Observer {
            if (it.equals(LoaderStatus.loading))
                showProgress()
            else
                hideProgress()
        })

        viewModel.errorMediatorLiveData.observe(this, Observer {
            it?.let {
                var updatedErrorMessage: String? = null
                if (it.contains("_")) {
                    updatedErrorMessage = it.replace("_", " ")
                    showSnackbar(updatedErrorMessage.lowercase(Locale.ROOT))
                } else {
                    updatedErrorMessage = it
                }


                onErrorCalled(updatedErrorMessage.lowercase(Locale.ROOT))

            }
        })

        initObservers()

    }

    protected abstract fun onErrorCalled(it: String?)

    //To show loader progress
    open fun showProgress() {
        hideKeyboard()
        if (!progressShown) {
            mBaseView!!.addView(mLoaderView)
            progressShown = true
        }
    }

    //To hide loader progress
    open fun hideProgress() {
        if (progressShown) {
            mBaseView!!.removeView(mLoaderView)
            progressShown = false
        }
    }


    //To show alert dialog with 'ok' button alone
    protected fun showAlertDialogOk(
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener = defaultDialogClickListener
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(getString(R.string.ok), listener)
        val mAlertDialog = builder.create()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.setCancelable(false)

        mAlertDialog.setOnShowListener {
            mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.primary))
        }

        mAlertDialog.show()
    }

    //To show alert dialog with Positive and Negative button with positive button listener alone
    protected fun showConfirmation(
        negativeText: String,
        positiveText: String,
        title: String,
        message: String,
        listener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText, listener)
        builder.setNegativeButton(negativeText) { dialog, _ -> dialog.dismiss() }
        val mAlertDialog = builder.create()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.setCancelable(false)

        mAlertDialog.setOnShowListener {
            mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.primary))
            mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.primary))
        }


        mAlertDialog.show()
    }

    //To show alert dialog with Positive and Negative button with positive and negative button listener
    protected fun showConfirmation(
        negativeText: String,
        positiveText: String,
        title: String?,
        message: String,
        listener: DialogInterface.OnClickListener,
        negativeListener: DialogInterface.OnClickListener
    ) {
        val builder = AlertDialog.Builder(this)
        if (title != null)
            builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton(positiveText, listener)
        builder.setNegativeButton(negativeText, negativeListener)
        val mAlertDialog = builder.create()
        mAlertDialog.setCanceledOnTouchOutside(false)
        mAlertDialog.setCancelable(false)

        mAlertDialog.setOnShowListener({
            mAlertDialog.getButton(AlertDialog.BUTTON_POSITIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.primary))
            mAlertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)
                .setTextColor(ContextCompat.getColor(this, R.color.primary))
        })

        mAlertDialog.show()
    }


    abstract fun initObservers()


    protected fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}
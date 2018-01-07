package com.yitu.etu.ui.activity

import android.app.Activity
import android.content.Intent
import com.mylhyl.zxing.scanner.decode.QRDecode
import com.yitu.etu.R
import kotlinx.android.synthetic.main.activity_capture.*

class CaptureActivity : BaseActivity() {

    override fun getLayout(): Int = R.layout.activity_capture

    override fun initActionBar() {
        title="二维码扫描"
        setRightText("相册") {
            Single()
        }
    }

    override fun initView() {

    }

    override fun getData() {

    }

    override fun initListener() {
        scanner_view.setOnScannerCompletionListener { rawResult, parsedResult, barcode ->
            finishResult(rawResult.text)
        }
    }

    override fun imageResult(path: String?) {
        QRDecode.decodeQR(path){ rawResult, parsedResult, barcode ->
            finishResult(rawResult.text)
        }

    }

    fun finishResult(content: String?) {
        val intent = Intent()
        intent.putExtra("content", content)
        setResult(Activity.RESULT_OK,intent)
        finish()
    }

    override fun onResume() {
        scanner_view.onResume()
        super.onResume()
    }

    override fun onPause() {
        scanner_view.onPause()
        super.onPause()
    }
}

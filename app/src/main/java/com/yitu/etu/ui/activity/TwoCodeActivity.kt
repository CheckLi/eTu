package com.yitu.etu.ui.activity

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.net.Uri
import com.google.zxing.BarcodeFormat
import com.google.zxing.EncodeHintType
import com.google.zxing.WriterException
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import com.yitu.etu.R
import com.yitu.etu.util.ImageUtil
import com.yitu.etu.util.addHost
import com.yitu.etu.util.userInfo
import com.yitu.etu.widget.image.QRCodeWriter
import io.rong.message.ImageMessage
import kotlinx.android.synthetic.main.activity_two_code.*
import java.io.File


class TwoCodeActivity : BaseActivity() {

    var bitmap:Bitmap?=null
    var path:String=""
    override fun getLayout(): Int = R.layout.activity_two_code
    override fun initActionBar() {
        title = "我的二维码"
        path=filesDir.absolutePath+"/twocode.png"
        setRightText("发到聊天") {
            if(bitmap!=null) {
                ImageUtil.saveBitmap(path, bitmap)
                val mes = ImageMessage.obtain(Uri.fromFile(File(path)), Uri.fromFile(File(path)))
                ShareFriendActivity.startActivity(this,mes)
            }else{
                showToast("请重新打开二维码页面")
            }
        }
    }

    override fun initView() {
        val tag = object : Target {
            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
            }

            override fun onBitmapFailed(errorDrawable: Drawable?) {
            }

            override fun onBitmapLoaded(bitmap: Bitmap, from: Picasso.LoadedFrom?) {
                tv_two_code.setImageBitmap(addLogo(generateBitmap("etuXs72hfjepIdnv76://ID"+userInfo().id, (150 * resources.displayMetrics.density).toInt(), (150 * resources.displayMetrics.density).toInt()), bitmap))
            }

        }
        Picasso.with(this)
                .load(userInfo().header.addHost())
                .config(Bitmap.Config.RGB_565)
                .resize((20 * resources.displayMetrics.density).toInt(), (20 * resources.displayMetrics.density)
                        .toInt())
                .into(tag)

    }

    override fun getData() {
    }

    override fun initListener() {
    }

    private fun generateBitmap(content: String, width: Int, height: Int): Bitmap? {
        val qrCodeWriter = QRCodeWriter()
        val hints = hashMapOf<EncodeHintType, String>()
        hints.put(EncodeHintType.CHARACTER_SET, "utf-8")
        try {
            val encode = qrCodeWriter.encode(content, BarcodeFormat.QR_CODE, width, height, hints)
            val pixels = IntArray(width * height)
            for (i in 0 until height) {
                for (j in 0 until width) {
                    if (encode.get(j, i)) {
                        pixels[i * width + j] = 0x00000000
                    } else {
                        pixels[i * width + j] = -0x1
                    }
                }
            }
            return Bitmap.createBitmap(pixels, 0, width, width, height, Bitmap.Config.RGB_565)
        } catch (e: WriterException) {
            e.printStackTrace()
        }

        return null
    }

    /**
     * 带log
     */
    private fun addLogo(qrBitmap: Bitmap?, logoBitmap: Bitmap): Bitmap? {
        if (qrBitmap == null) {
            return null
        }
        val qrBitmapWidth = qrBitmap?.width ?: 0
        val qrBitmapHeight = qrBitmap?.height ?: 0
        val logoBitmapWidth = logoBitmap.width
        val logoBitmapHeight = logoBitmap.height
        val blankBitmap = Bitmap.createBitmap(qrBitmapWidth, qrBitmapHeight, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(blankBitmap)
        canvas.drawBitmap(qrBitmap, 0f, 0f, null)
        canvas.save(Canvas.ALL_SAVE_FLAG)
        var scaleSize = 1.0f
        while (logoBitmapWidth / scaleSize > qrBitmapWidth / 5 || logoBitmapHeight / scaleSize > qrBitmapHeight / 5) {
            scaleSize *= 2f
        }
        val sx = 1.0f / scaleSize
        canvas.scale(sx, sx, qrBitmapWidth * 0.5f, qrBitmapHeight * 0.5f)
        canvas.drawBitmap(logoBitmap, (qrBitmapWidth - logoBitmapWidth) * 0.5f, (qrBitmapHeight - logoBitmapHeight) * 0.5f, null)
        canvas.restore()
        bitmap=blankBitmap
        return blankBitmap
    }

    override fun onDestroy() {
        bitmap=null
        super.onDestroy()
    }
}

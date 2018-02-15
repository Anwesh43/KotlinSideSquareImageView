package ui.anwesome.com.sidesquareimageview

/**
 * Created by anweshmishra on 16/02/18.
 */
import android.content.*
import android.graphics.*
import android.view.*
class SideSquareImageView(ctx:Context, var bitmap: Bitmap):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    override fun draw(canvas:Canvas) {

    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }
}
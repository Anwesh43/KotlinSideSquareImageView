package ui.anwesome.com.sidesquareimageview

/**
 * Created by anweshmishra on 16/02/18.
 */
import android.app.Activity
import android.content.*
import android.graphics.*
import android.view.*
class SideSquareImageView(ctx:Context, var bitmap: Bitmap):View(ctx) {
    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val renderer = SideSquareImageRenderer(this)
    override fun draw(canvas:Canvas) {
        renderer.render(canvas, paint)
    }
    override fun onTouchEvent(event:MotionEvent):Boolean {
        when(event.action) {
            MotionEvent.ACTION_DOWN -> {
                renderer.handleTap()
            }
        }
        return true
    }
    data class State(var scale:Float = 0f, var dir:Float = 0f, var prevScale: Float = 0f) {
        fun update(stopcb: (Float) -> Unit) {
            scale += 0.1f*dir
            if(Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                stopcb(scale)
            }
        }
        fun startUpdating(startcb: () -> Unit) {
            if(dir == 0f) {
                dir = 1 - 2*prevScale
                startcb()
            }
        }
    }
    data class Animator(var view:View, var animated:Boolean = false) {
        fun animate(updatecb : () -> Unit) {
            if(animated) {
                updatecb()
                try {
                    Thread.sleep(50)
                    view.invalidate()
                }
                catch(ex : Exception) {

                }
            }
        }
        fun start() {
            if(!animated) {
                animated = true
                view.postInvalidate()
            }
        }
        fun stop() {
            if(animated) {
                animated = false
            }
        }
    }
    data class SideSquareImage(var bitmap : Bitmap) {
        val state = State()
        fun draw(canvas:Canvas, paint:Paint) {
            val w = bitmap.width.toFloat()
            val h = bitmap.height.toFloat()
            canvas.save()
            paint.color = Color.BLACK
            canvas.drawBitmap(bitmap, 0f, 0f, paint)
            paint.color = Color.parseColor("#99212121")
            canvas.drawRect(RectF(0f,0f,(w/3) * state.scale,h), paint)
            canvas.restore()
        }
        fun update(stopcb: (Float) -> Unit) {
            state.update(stopcb)
        }
        fun startUpdating(startcb: () -> Unit) {
            state.startUpdating(startcb)
        }
    }
    data class SideSquareImageRenderer(var view: SideSquareImageView, var time: Int = 0) {
        var sideSquareImage: SideSquareImage ?= null
        val animator = Animator(view)
        fun render(canvas: Canvas, paint: Paint) {
            if(time == 0) {
                val w = canvas.width
                val h = canvas.height
                val bitmap = Bitmap.createScaledBitmap(view.bitmap, w, h, true)
                sideSquareImage = SideSquareImage(bitmap)
            }
            sideSquareImage?.draw(canvas,paint)
            time++
            animator.animate {
                sideSquareImage?.update {
                    animator.stop()
                }
            }
        }
        fun handleTap() {
            sideSquareImage?.startUpdating {
                animator.start()
            }
        }
    }
    companion object {
        fun create(activity: Activity, bitmap: Bitmap): SideSquareImageView {
            val view = SideSquareImageView(activity,bitmap)
            activity.setContentView(view)
            return view
        }
    }
}
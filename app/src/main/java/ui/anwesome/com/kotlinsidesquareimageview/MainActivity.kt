package ui.anwesome.com.kotlinsidesquareimageview

import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import ui.anwesome.com.sidesquareimageview.SideSquareImageView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        SideSquareImageView.create(this, BitmapFactory.decodeResource(resources, R.drawable.nature_more))
    }
}

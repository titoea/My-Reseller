package com.example.myreseller.utils
/*
* Custom font class for bold textviews
*/
import android.content.Context
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView

class MRTextViewBold(context : Context, attrs: AttributeSet): AppCompatTextView(context, attrs) {
    init{
        // applies the font to the components
        applyFont()
    }
    private fun applyFont(){
        val typeface: Typeface = Typeface.createFromAsset(context.assets, "Montserrat-Bold.ttf")
        setTypeface(typeface)
    }

}
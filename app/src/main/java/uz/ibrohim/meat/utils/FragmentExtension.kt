package uz.ibrohim.meat.utils

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Build
import android.os.Environment
import androidx.activity.result.ActivityResultLauncher
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.clk.progress.CircularProgress
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.floatingactionbutton.FloatingActionButton
import es.dmoral.toasty.Toasty
import java.text.SimpleDateFormat
import java.util.Date

fun Fragment.warningToast(text: String) {
    Toasty.warning(requireContext(), text, Toasty.LENGTH_SHORT, true).show()
}

fun warningToast(context: Context, text: String) {
    Toasty.warning(context, text, Toasty.LENGTH_SHORT, true).show()
}

fun Fragment.errorToast(text: String) {
    Toasty.error(requireContext(), text, Toasty.LENGTH_SHORT, true).show()
}

fun errorToast(context: Context, text: String) {
    Toasty.error(context, text, Toasty.LENGTH_SHORT, true).show()
}

fun Fragment.successToast(text: String) {
    Toasty.success(requireContext(), text, Toasty.LENGTH_SHORT, true).show()
}

fun successToast(context: Context, text: String) {
    Toasty.success(context, text, Toasty.LENGTH_SHORT, true).show()
}

fun progressInterface(circularProgress: CircularProgress) {
    circularProgress.setColor(Color.RED, Color.BLUE, Color.YELLOW, Color.GREEN)
    circularProgress.setBodyColor(com.clk.progress.R.color.blue)
    circularProgress.setRotationSpeeed(25)
}

@SuppressLint("SimpleDateFormat")
fun todayDate(): String {
    val currentDate: String
    val simpleDate = SimpleDateFormat("dd.MM.yyyy")
    currentDate = simpleDate.format(Date())
    return currentDate
}

fun Fragment.requestStoragePermission(
    REQUEST_PERMISSIONS: Int, imageProfile: ActivityResultLauncher<Intent>
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (!Environment.isExternalStorageManager()) {

            ImagePicker.with(requireActivity())
                .galleryOnly()
                .createIntent { intent ->
                    imageProfile.launch(intent)
                }

        } else {
            if ((ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                ) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    requireActivity(), arrayOf(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_PERMISSIONS
                )
            }
        }
    } else {
        if ((ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            ) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                requireActivity(), arrayOf(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), REQUEST_PERMISSIONS
            )
        } else {
            ImagePicker.with(requireActivity())
                .galleryOnly()
                .createIntent { intent ->
                    imageProfile.launch(intent)
                }
        }
    }
}

fun requestStoragePermission(
    REQUEST_PERMISSIONS: Int, imageProfile: ActivityResultLauncher<Intent>, activity: Activity,
    context: Context
) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
        if (!Environment.isExternalStorageManager()) {

            ImagePicker.with(activity)
                .galleryOnly()
                .createIntent { intent ->
                    imageProfile.launch(intent)
                }

        } else {
            if ((ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                ) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                    context,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ) != PackageManager.PERMISSION_GRANTED)
            ) {
                ActivityCompat.requestPermissions(
                    activity, arrayOf(
                        Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE
                    ), REQUEST_PERMISSIONS
                )
            }
        }
    } else {
        if ((ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            ) != PackageManager.PERMISSION_GRANTED) && (ContextCompat.checkSelfPermission(
                context,
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED)
        ) {
            ActivityCompat.requestPermissions(
                activity, arrayOf(
                    Manifest.permission.MANAGE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), REQUEST_PERMISSIONS
            )
        } else {
            ImagePicker.with(activity)
                .galleryOnly()
                .createIntent { intent ->
                    imageProfile.launch(intent)
                }
        }
    }
}

fun floatingScroll(recycler: RecyclerView, floating: FloatingActionButton) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        recycler.setOnScrollChangeListener { _, _, _, _, oldScrollY ->
            if (oldScrollY < 0 && floating.isShown) {
                floating.hide()
            } else if (oldScrollY > 22) {
                floating.show()
            }
        }
    } else {
        recycler.viewTreeObserver.addOnScrollChangedListener {
            val mScrollY: Int = recycler.scrollY
            if (mScrollY > 0 && floating.isShown) {
                floating.hide()
            } else if (mScrollY < 22) {
                floating.show()
            }
        }
    }
}

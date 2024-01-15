import androidx.fragment.app.Fragment

fun Fragment.clearFocus() {
    activity?.currentFocus?.clearFocus()
}
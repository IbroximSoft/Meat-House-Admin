package uz.ibrohim.meat.category

import android.app.Activity
import android.app.Dialog
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import com.clk.progress.CircularProgress
import com.github.dhaval2404.imagepicker.ImagePicker
import com.github.dhaval2404.imagepicker.util.FileUriUtils
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import uz.ibrohim.meat.R
import uz.ibrohim.meat.category.request_response.CategoryResponse
import uz.ibrohim.meat.category.request_response.CategoryResponseItem
import uz.ibrohim.meat.category.services.CategoryViewModel
import uz.ibrohim.meat.databinding.FragmentCategoryBinding
import uz.ibrohim.meat.retrofit.ApiClient
import uz.ibrohim.meat.retrofit.NetworkHelper
import uz.ibrohim.meat.retrofit.Resource
import uz.ibrohim.meat.utils.errorToast
import uz.ibrohim.meat.utils.floatingScroll
import uz.ibrohim.meat.utils.progressInterface
import uz.ibrohim.meat.utils.requestStoragePermission
import uz.ibrohim.meat.utils.warningToast
import java.io.File
import java.io.FileOutputStream

class CategoryFragment : Fragment() {

    private var _binding: FragmentCategoryBinding? = null
    private val binding get() = _binding!!
    private var filePath = ""
    private val REQUEST_PERMISSIONS = 1
    private lateinit var imageView: ImageView
    private lateinit var imageIcon: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCategoryBinding.inflate(inflater, container, false)

        binding.apply {

            getRequestCategory()
            floatingScroll(categoryRv, categoryAdd)
            categoryAdd.setOnClickListener {
                getDialog()
            }
        }

        return binding.root
    }

    private fun getRequestCategory() {
        val networkHelper = NetworkHelper(requireContext())
        val viewModel = CategoryViewModel(ApiClient.apiCategoryServices, networkHelper)
        viewModel.getCategory()
        lifecycleScope.launch {
            viewModel.flow.collect {
                when (it) {
                    is Resource.Error -> {
                        binding.apply {
                            if (it.message != "Sever Error !"){
                                errorToast("Internet bilan aloqa yo'q")
                            }
                            categoryProgress.isVisible = false
                            categoryRv.isVisible = true
                        }
                        Log.d("test777", it.message)
                    }

                    Resource.Loading -> {
                        binding.apply {
                            categoryRv.isVisible = false
                            categoryProgress.isVisible = true
                        }
                    }

                    is Resource.Success -> {
                        binding.apply {
                            categoryProgress.isVisible = false
                            categoryRv.isVisible = true
                        }
                        successData(it.data as CategoryResponse, viewModel)
                    }
                }
            }
        }
    }

    private fun successData(responseCategory: CategoryResponse, viewModel: CategoryViewModel) {
        val adapter = CategoryAdapter(responseCategory, object :
            CategoryAdapter.OnItemClickListener {
            override fun onItemClick(student: CategoryResponseItem, position: Int) {}
        })

        binding.apply {
            if (responseCategory.isNotEmpty()) {
                categoryRv.isVisible = true
                categoryEmpty.isVisible = false
            } else {
                categoryRv.isVisible = false
                categoryEmpty.isVisible = true
            }
        }

        binding.categoryRv.adapter = adapter
    }

    private fun getDialog() {
        val dialog = Dialog(requireContext())
        dialog.setContentView(R.layout.category_item_add)
        dialog.setCanceledOnTouchOutside(false)
        val width = (resources.displayMetrics.widthPixels * 0.90).toInt()
        val height = (resources.displayMetrics.heightPixels * 0.90).toInt()
        dialog.window!!.setLayout(
            width,
            height
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.show()

        val editText = dialog.findViewById<EditText>(R.id.category_add_edit)
        val button = dialog.findViewById<AppCompatButton>(R.id.category_add_btn)
        imageIcon = dialog.findViewById(R.id.product_add_icon)
        imageView = dialog.findViewById(R.id.category_add_image)
        val linear = dialog.findViewById<LinearLayout>(R.id.category_add_progress)
        val progress = dialog.findViewById<CircularProgress>(R.id.category_add_circularProgress)

        progressInterface(progress)

        imageView.setOnClickListener {
            requestStoragePermission(REQUEST_PERMISSIONS, imageProfile, requireActivity(), context = requireContext())
        }

        button.setOnClickListener {
            val category = editText.text.toString()
            if (category.isEmpty()) {
                warningToast("Kategoriya nomini kiriting!")
            } else {
                val compressionRatio = 2 //1 == originalImage, 2 = 50% compression, 4=25% compress
                val file = File(filePath)
                try {
                    val bitmap = BitmapFactory.decodeFile(file.path)
                    bitmap.compress(
                        Bitmap.CompressFormat.JPEG,
                        compressionRatio,
                        FileOutputStream(file)
                    )
                } catch (t: Throwable) {
                    Log.e("ERROR", "Error compressing file.$t")
                    t.printStackTrace()
                }

//                val imageFile = File(filePath)
                val multipartBody = MultipartBody.Part.createFormData(
                    "image",
                    File(file.toURI()).name,
                    File(file.toURI()).asRequestBody("multipart/form-data".toMediaTypeOrNull())
                )

                val requestBody = category.toRequestBody(MultipartBody.FORM)
                addCategory(requestBody, multipartBody, linear, button, dialog)
            }
        }
    }

    private val imageProfile =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data

            when (resultCode) {
                Activity.RESULT_OK -> {
                    val uri: Uri = data?.data!!
                    filePath = FileUriUtils.getRealPath(requireContext(), uri).toString()
                    val bitmapImage = BitmapFactory.decodeFile(filePath)
                    val nh = (bitmapImage.height * (512.0 / bitmapImage.width)).toInt()
                    val scaled = Bitmap.createScaledBitmap(bitmapImage, 512, nh, true)
                    imageView.setImageBitmap(scaled)
                    imageIcon.isVisible = false
                    binding.apply {
//                        imageView.setImageURI(Uri.fromFile(File(filePath)))
                    }

                }
                ImagePicker.RESULT_ERROR -> {
                    Toast.makeText(
                        requireContext(), ImagePicker.getError(data),
                        Toast.LENGTH_SHORT
                    ).show()
                }
                else -> {
                    Toast.makeText(requireContext(), "Task Cancelled", Toast.LENGTH_SHORT).show()
                }
            }
        }

    private fun addCategory(name: RequestBody, request: MultipartBody.Part, linear: LinearLayout, button: AppCompatButton, dialog: Dialog) {
        warningToast("Shu yerga keldi")
        val networkHelper = NetworkHelper(requireContext())
        val viewModel = CategoryViewModel(ApiClient.apiCategoryServices, networkHelper)
//        val model = CategoryRequest(name = name, order = 21324)
        val parent = "".toRequestBody(MultipartBody.FORM)
        if (filePath.isNotEmpty()){
            viewModel.getAddCategory(name = name, request = request, parent = parent)
            lifecycleScope.launch {
                viewModel.flow.collect {
                    when (it) {
                        is Resource.Error -> {
                            warningToast(it.message)
                            linear.isVisible = false
                            button.isVisible = true
                        }

                        Resource.Loading -> {
                            button.isVisible = false
                            linear.isVisible = true
                        }

                        is Resource.Success -> {
                            Log.d("test777", "${it.data}")
                            dialog.dismiss()
                        }
                    }
                }
            }
        }
    }
}
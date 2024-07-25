package com.example.umc_stepper.test

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.umc_stepper.R
import java.util.Collections

class Test : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var imagesAdapter: ImagesAdapter
    private val selectedImages = mutableListOf<String>()
    private lateinit var imagePickerLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        recyclerView = findViewById(R.id.recyclerView)
        imagesAdapter = ImagesAdapter(selectedImages)
        recyclerView.adapter = imagesAdapter
        recyclerView.layoutManager = GridLayoutManager(this, 3)

        initImagePickerLauncher()
        openImagePicker()

        val itemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.Callback() {
            override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
                val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
                return makeMovementFlags(dragFlags, 0)
            }

            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                val fromPosition = viewHolder.adapterPosition
                val toPosition = target.adapterPosition
                Collections.swap(selectedImages, fromPosition, toPosition)
                imagesAdapter.notifyItemMoved(fromPosition, toPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                // Do nothing
            }
        })

        itemTouchHelper.attachToRecyclerView(recyclerView)
    }

    private fun initImagePickerLauncher() {
        imagePickerLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) { result: ActivityResult ->
            if (result.resultCode == AppCompatActivity.RESULT_OK) {
                result.data?.clipData?.let { clipData ->
                    for (i in 0 until clipData.itemCount) {
                        val imageUri = clipData.getItemAt(i).uri
                        selectedImages.add(imageUri.toString())
                    }
                    imagesAdapter.notifyDataSetChanged()
                } ?: result.data?.data?.let { uri ->
                    selectedImages.add(uri.toString())
                    imagesAdapter.notifyDataSetChanged()
                }
            }
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        imagePickerLauncher.launch(intent)
    }

}

class ImagesAdapter(private val images: List<String>) : RecyclerView.Adapter<ImagesAdapter.ImageViewHolder>() {

    inner class ImageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        val imageUri = images[position]
        Glide.with(holder.imageView.context)
            .load(imageUri)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}

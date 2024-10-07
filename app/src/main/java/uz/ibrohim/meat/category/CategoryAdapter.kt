package uz.ibrohim.meat.category

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import uz.ibrohim.meat.category.request_response.CategoryResponseItem
import uz.ibrohim.meat.databinding.CategoryItemBinding

class CategoryAdapter(
    private val list: List<CategoryResponseItem>,
    val listener: OnItemClickListener
) :
    RecyclerView.Adapter<CategoryAdapter.Vh>() {

    inner class Vh(private val itemAdapterItemBinding: CategoryItemBinding) :
        RecyclerView.ViewHolder(itemAdapterItemBinding.root) {

        fun onBind(student: CategoryResponseItem) {
            itemAdapterItemBinding.categoryTxt.text = student.name
            Glide.with(itemView.context).load(student.image).into(itemAdapterItemBinding.categoryImg)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Vh {
        return Vh(CategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: Vh, position: Int) {
        holder.onBind(list[position])
    }

    interface OnItemClickListener {
        fun onItemClick(student: CategoryResponseItem, position: Int)
    }

    override fun getItemCount(): Int {
        return list.size
    }
}
package com.kg.automatik

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

import com.kg.automatik.databinding.ItemBinding

class FragmentImageAdapter(private var itemList:ArrayList<LabelModel> ): RecyclerView.Adapter<FragmentImageAdapter.ViewHolder>() {


   //private lateinit var itemList:ArrayList<LabelModel>


    class ViewHolder(private val binding: ItemBinding):RecyclerView.ViewHolder(binding.root)
    {


        fun bind(labelModel: LabelModel)
        {

            binding.apply {


                textLabel.text=labelModel.label
                var conf:Float=labelModel.confidence.toFloat()
                conf *= 100
                val confidence :String= String.format("%.2f",conf)+"%"
                textConfidence.text=confidence


            }



        }


    }

    fun addList(list:ArrayList<LabelModel>)
    {
        itemList= ArrayList()
        itemList=list
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding=ItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {

        return itemList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       val labelModel=itemList.get(position)



        if(labelModel!=null)
        {
            holder.bind(labelModel)
        }

    }

}
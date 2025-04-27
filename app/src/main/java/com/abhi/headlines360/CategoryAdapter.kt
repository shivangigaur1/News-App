package com.abhi.headlines360

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CategoryAdapter(
    private val categories: List<String>,
    private val fetchNews: (String) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        // Inflate the category page layout, which contains a RecyclerView
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_page_layout, parent, false)
        return CategoryViewHolder(view)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]

        // Fetch and display news for this category
        fetchNews(category)
    }

    override fun getItemCount(): Int = categories.size

    class CategoryViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        // Find RecyclerView in the category page layout
        val recyclerView: RecyclerView = view.findViewById(R.id.recyclerViewNews)
    }
}

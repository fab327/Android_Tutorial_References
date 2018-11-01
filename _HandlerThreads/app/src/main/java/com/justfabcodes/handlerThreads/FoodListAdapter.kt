/*
 * Copyright (c) 2018 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * Notwithstanding the foregoing, you may not use, copy, modify, merge, publish,
 * distribute, sublicense, create a derivative work, and/or sell copies of the
 * Software in any work that is designed, intended, or marketed for pedagogical or
 * instructional purposes related to programming, coding, application development,
 * or information technology.  Permission for such use, copying, modification,
 * merger, publication, distribution, sublicensing, creation of derivative works,
 * or sale is expressly withheld.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 *
 */

package com.justfabcodes.handlerThreads

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.food_list_item.view.*

class FoodListAdapter(
    private val foodOrderList: MutableList<FoodOrder>,
    private val context: Context
) : RecyclerView.Adapter<FoodListAdapter.FoodViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {
        val view = LayoutInflater.from(context).inflate(
            R.layout.food_list_item,
            parent, false
        )
        return FoodViewHolder(view)
    }

    override fun getItemCount(): Int {
        return foodOrderList.size
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.bindItems(foodOrderList[position])
    }

    fun getOrderList(): MutableList<FoodOrder> {
        return foodOrderList
    }

    class FoodViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bindItems(food: FoodOrder) {
            itemView.foodNameTextView.text =
                    itemView.context.getString(R.string.food_text, food.foodName)
            itemView.foodPriceTextView.text =
                    itemView.context.getString(R.string.price_text, food.foodPrice.toString())
            itemView.sideOrderTextView.text =
                    itemView.context.getString(R.string.side_order_text, food.sideOrder)
        }
    }
}

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

import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.ref.WeakReference

class MainActivity : AppCompatActivity() {

    private lateinit var foodListAdapter: FoodListAdapter
    private lateinit var uiHandler: UiHandler
    private lateinit var foodRunnable: FoodRunnable
    private lateinit var orderHandlerThread: OrderHandlerThread

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        foodListAdapter = FoodListAdapter(mutableListOf(), applicationContext)
        orderRecyclerView.layoutManager = LinearLayoutManager(this)
        orderRecyclerView.adapter = foodListAdapter
        uiHandler = UiHandler()
        uiHandler.setRecyclerView(orderRecyclerView)
        uiHandler.setAdapter(foodListAdapter)
    }

    override fun onStart() {
        super.onStart()

        orderHandlerThread = OrderHandlerThread(uiHandler)
        orderHandlerThread.start()
        foodRunnable = FoodRunnable(orderHandlerThread)
        foodRunnable.setMaxOrders(10)
        foodRunnable.start()
    }

    override fun onDestroy() {
        super.onDestroy()

        foodRunnable.stop()
        orderHandlerThread.quit()
    }

    class UiHandler : Handler() {

        private lateinit var weakRefFoodListAdapter: WeakReference<FoodListAdapter>
        private lateinit var weakRefOrderRecyclerView: WeakReference<RecyclerView>

        override fun handleMessage(msg: Message?) {
            super.handleMessage(msg)

            val position = weakRefFoodListAdapter.get()?.getOrderList()?.size
            addAndNotifyForOrder(msg?.obj as FoodOrder, position!!)
            weakRefOrderRecyclerView.get()?.smoothScrollToPosition(weakRefFoodListAdapter.get()?.itemCount!!)
        }

        fun setAdapter(foodListAdapter: FoodListAdapter) {
            weakRefFoodListAdapter = WeakReference(foodListAdapter)
        }

        fun setRecyclerView(foodRecyclerView: RecyclerView) {
            weakRefOrderRecyclerView = WeakReference(foodRecyclerView)
        }

        private fun addAndNotifyForOrder(foodOrder: FoodOrder, position: Int) {
            weakRefFoodListAdapter.get()?.getOrderList()?.add(foodOrder)
            weakRefOrderRecyclerView.get()?.adapter?.notifyItemInserted(position)
        }

    }
}

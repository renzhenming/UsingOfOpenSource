package com.rzm.testapplication.kotllincoroutine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.rzm.testapplication.R
import kotlinx.coroutines.*
import java.util.concurrent.Executors

class CoroutineKotlinActivity : AppCompatActivity() {
    companion object {
        const val TAG = "CoroutineActivity"
    }

    /**
     * 使用官方库的 MainScope()获取一个协程作用域用于创建协程
     * GlobalScope在 Android 开发中同样不推荐这种用法，因为它的生命周期会只受整个应用程序的生命周期限制，且不能取消。
     * 自行通过 CoroutineContext 创建一个 CoroutineScope 对象,比较推荐的使用方法，我们可以通过 context
     * 参数去管理和控制协程的生命周期（这里的 context 和 Android 里的不是一个东西，是一个更通用的概念，会有一个
     * Android 平台的封装来配合使用）。
    val coroutineScope = CoroutineScope(context)
    coroutineScope.launch {
    ...
    }
     */
    private val mScope = MainScope()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_coroutine)

        Log.i(TAG, "================launch1111111===============")
        // 创建一个默认参数的协程，其默认的调度模式为Main 也就是说该协程的线程环境是Main线程
        mScope.launch {
            // 这里就是协程体
            Log.i(TAG, "================launch inner 1111111 start===============")
            // 延迟1000毫秒  delay是一个挂起函数
            // 在这1000毫秒内该协程所处的线程不会阻塞
            // 协程将线程的执行权交出去，该线程该干嘛干嘛，到时间后会恢复至此继续向下执行
            delay(1000)
            Log.i(TAG, "================launch inner 1111111 end===============")
        }
        Log.i(TAG, "================launch1111111 end===============")

        Log.i(TAG, "================launch2222222===============")
        // 创建一个指定了调度模式的协程，该协程的运行线程为IO线程
        mScope.launch(Dispatchers.IO) {
            Log.i(TAG, "================launch inner 2222222 start===============")
            delay(1000)
            // 此处是IO线程模式
            // 切线程 将协程所处的线程环境切至指定的调度模式Main
            withContext(Dispatchers.Main) {
                // 现在这里就是Main线程了  可以在此进行UI操作了
            }
            Log.i(TAG, "================launch inner 2222222 end===============")
        }
        Log.i(TAG, "================launch2222222 end===============")

        Log.i(TAG, "================launch3333333===============")
        // 下面直接看一个例子： 从网络中获取数据  并更新UI
        // 该例子不会阻塞主线程
        mScope.launch(Dispatchers.IO) {
            // 执行getUserInfo方法时会将线程切至IO去执行
            Log.i(TAG, "================launch inner 3333333 start===============")
            val userInfo = getUserInfo()
            // 获取完数据后 切至Main线程进行更新UI
            withContext(Dispatchers.Main) {
                // 更新UI
                Log.i(TAG, "更新UI,userInfo = $userInfo")
            }
            Log.i(TAG, "================launch inner 3333333 end===============")
        }
        Log.i(TAG, "================launch3333333 end===============")
    }

    private suspend fun getUserInfo(): String {
        return withContext(Dispatchers.IO) {
            delay(2000)
            "我是用户信息"
        }
    }

    /**
     * 不要使用 GlobalScope 去启动协程，因为 GlobalScope 启动的协程生命周期与应用程序的生命周期一致，无法取消
     */
    fun startCoroutineTask(view: View) {
        Log.i(TAG, "================1111111===============")
        GlobalScope.launch {
            Log.i(TAG, "startCoroutineTask start")
            var r1 = suspendFunction1()
            var r2 = suspendFunction2()
            Log.i(TAG, "startCoroutineTask end")
        }
        Log.i(TAG, "================22222222===============")
    }

    private suspend fun suspendFunction1(): Int {
        Log.i(TAG, "suspendFunction1 start")
        delay(2000)
        Log.i(TAG, "suspendFunction1 end")
        return 1
    }

    private suspend fun suspendFunction2(): Int {
        Log.i(TAG, "suspendFunction2 start")
        delay(2000)
        Log.i(TAG, "suspendFunction2 end")
        return 2
    }

    fun startAsyncTask(view: View) {
        Log.i(TAG, "startAsyncTask")
        mScope.launch {
            Log.i(TAG, "startAsyncTask inner")
            // 开启一个IO模式的线程 并返回一个Deferred，Deferred可以用来获取返回值
            // 代码执行到此处时会新开一个协程 然后去执行协程体  父协程的代码会接着往下走
            var result = async(Dispatchers.IO) {
                Log.i(TAG, "startAsyncTask inner io start")
                delay(2000)
                Log.i(TAG, "startAsyncTask inner io end")
                //返回一个值
                "renzhenming async"
            }
            var value = result.await()
            Log.i(TAG, "startAsyncTask value = $value")
        }
        Log.i(TAG, "startAsyncTask end")
    }

    fun startMultiAsyncTask(view: View) {
        mScope.launch {
            val job1 = async {
                // 请求1
                delay(5000)
                "1"
            }
            val job2 = async {
                delay(5000)
                "2"
            }
            val job3 = async {
                // 请求3
                delay(5000)
                "3"
            }
            val job4 = async {
                // 请求4
                delay(5000)
                "4"
            }
            val job5 = async {
                // 请求5
                delay(5000)
                "5"
            }
            // 代码执行到此处时  5个请求已经同时在执行了
            // 等待各job执行完 将结果合并
            Log.d(
                TAG,
                "startMultiAsyncTask: ${job1.await()} ${job2.await()} ${job3.await()} ${job4.await()} ${job5.await()}"
            )
        }
    }

    fun scopeTest(){
        val dispatcher = Executors.newFixedThreadPool(1).asCoroutineDispatcher()
        val myScope = CoroutineScope(dispatcher)
        myScope.launch {
        }
    }
}
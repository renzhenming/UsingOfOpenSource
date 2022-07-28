package com.rzm.testapplication.anr.blockcanary

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.rzm.testapplication.R

/**
 * 常用adb命令
 *
 * 查看进程id:adb shell pidof com.rzm.testapplication
 * 查看系统进程信息：adb shell cat /prop/stat
 * 查看指定进程信息：adb shell cat /proc/30228/stat  30228是进程id
 *
 *
 *
 */
class BlockCanaryActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_canary)
    }

    ///proc/stat文件
    /**
     * proc/stat节点记录的是系统进程整体的统计信息:
     *
     * renzhenming@renzhenmingdeMacBook-Air ~ % adb shell cat /proc/stat
     * 对应关系：
     *
     * cpu指标 含义
     * user 用户态时间
     * nice 用户态时间(低优先级，nice>0)
     * system 内核态时间
     * idle 空闲时间
     * iowait I/O等待时间
     * irq 硬中断
     * softirq 软中断
     * iowait时间是不可靠值，理由如下：
     *
     * CPU指标：user，nice, system, idle, iowait, irq, softirq
     *
     * cpu  128242 12481 207502 10150034 621 18156 2862 0 0 0
     * cpu0 23765 3124 35292 2559913 285 5581 1106 0 0 0
     * cpu1 20444 3204 28116 2575643 115 3085 433 0 0 0
     * cpu2 57702 2946 106257 2457142 109 4953 950 0 0 0
     * cpu3 26329 3205 37835 2557334 110 4535 371 0 0 0
     * intr 11625461 0 0 0 7271808 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 78 13605 34927 1 55476 58 10 7244 151519 0 0 2 0 0 0 0 0 0 0 0 200 0 0 552 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 0 2
     * ctxt 21752126
     * btime 1658902314
     * processes 30122
     * procs_running 1
     * procs_blocked 0
     * softirq 4119810 1 1312260 472 28297 89736 0 151930 746204 257028 1533882
     *
     * intr：系统启动以来的所有interrupts的次数情况
     * ctxt: 系统上下文切换次数
     * btime：启动时长(单位:秒)，从Epoch(即1970零时)开始到系统启动所经过的时长，每次启动会改变。
     * 此处指为1500827856，转换北京时间为2017/7/24 0:37:36
     * processes：系统启动后所创建过的进程数量。当短时间该值特别大，系统可能出现异常
     * procs_running：处于Runnable状态的进程个数
     * procs_blocked：处于等待I/O完成的进程个数
     *
     *
     *
     * renzhenming@renzhenmingdeMacBook-Air ~ % adb shell cat /proc/30228/stat
     * 30228(进程ID.) (testapplication(task_struct结构体的进程名)) S(进程状态, 此处为S) 288( 父进程ID) 288(进程组ID) 0(进程会话组ID) 0(当前进程的tty终点设备号) -1 1077936448 54068 0 18 0 283 161 0 0 20 0 25 0 2693165 14244589568 49086 18446744073709551615 1 1 0 0 0 0 4612 1 1073775864 0 0 0 17 1 0 0 0 0 0 0 0 0 0 0 0 0 0
     *
     * 1557 (system_server) S 823 823 0 0 -1 1077952832 //1~9
     * 2085481 15248 2003 27 166114 129684 26 30  //10~17
     * 10 -10 221 0 2284 2790821888 93087 18446744073709551615 //18~25
     * 1 1 0 0 0 0 6660 0 36088 0 0 0 17 3 0 0 0 0 0 0 0 0 0 0 0 0 0
     *
     * pid： 进程ID.
     * comm: task_struct结构体的进程名
     * state: 进程状态, 此处为S
     * ppid: 父进程ID （父进程是指通过fork方式，通过clone并非父进程）
     * pgrp：进程组ID
     * session：进程会话组ID
     * tty_nr：当前进程的tty终点设备号
     * tpgid：控制进程终端的前台进程号
     * flags：进程标识位，定义在include/linux/sched.h中的PF_*, 此处等于1077952832
     * minflt： 次要缺页中断的次数，即无需从磁盘加载内存页. 比如COW和匿名页
     * cminflt：当前进程等待子进程的minflt
     * majflt：主要缺页中断的次数，需要从磁盘加载内存页. 比如map文件
     * majflt：当前进程等待子进程的majflt
     * utime: 该进程处于用户态的时间，单位jiffies，此处等于166114
     * stime: 该进程处于内核态的时间，单位jiffies，此处等于129684
     * cutime：当前进程等待子进程的utime
     * cstime: 当前进程等待子进程的utime
     * priority: 进程优先级, 此次等于10.
     * nice: nice值，取值范围[19, -20]，此处等于-10
     * num_threads: 线程个数, 此处等于221
     * itrealvalue: 该字段已废弃，恒等于0
     * starttime：自系统启动后的进程创建时间，单位jiffies，此处等于2284
     * vsize：进程的虚拟内存大小，单位为bytes
     * rss: 进程独占内存+共享库，单位pages，此处等于93087
     * rsslim: rss大小上限
     *
     * 说明：
     * 第10~17行主要是随着时间而改变的量；
     * 内核时间单位，sysconf(_SC_CLK_TCK)一般地定义为jiffies(一般地等于10ms)
     * starttime: 此值单位为jiffies, 结合/proc/stat的btime，可知道每一个线程启动的时间点
     * 1500827856 + 2284/100 = 1500827856, 转换成北京时间为2017/7/24 0:37:58
     * 第四行数据很少使用,只说一下该行第7至9个数的含义:
     * signal：即将要处理的信号，十进制，此处等于6660
     * blocked：阻塞的信号，十进制
     * sigignore：被忽略的信号，十进制，此处等于36088
     */
    fun click(view: View) {
        Thread.sleep(5000)
    }
}
for i in `seq 1 100`
do
   adb shell am force-stop com.rzm.testapplication
   sleep 1
   adb shell am start-activity -W -n com.rzm.testapplication/.MainActivity | grep "TotalTime" | cut -d ' ' -f 2
done


#renzhenming@renzhenmingdeMacBook-Air TestApplication % adb shell am start-activity -W -n com.rzm.testapplication/.MainActivity
#Starting: Intent { cmp=com.rzm.testapplication/.MainActivity }
#Warning: Activity not started, intent has been delivered to currently running top-most instance.
#Status: ok
#LaunchState: UNKNOWN (0)
#Activity: com.rzm.testapplication/.MainActivity
#TotalTime: 0
#WaitTime: 10
#Complete
#renzhenming@renzhenmingdeMacBook-Air TestApplication %
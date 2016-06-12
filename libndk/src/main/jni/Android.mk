
#定义变量LOCAL_PATH,并赋值为当前文件夹名称,即 jni
LOCAL_PATH := $(call my-dir)
###观察变量 LOCAL_PATH 的值
$(info LOCAL_PATH=$(LOCAL_PATH))

#定义变量MY_CPP_LIST, 并赋值为LOCAL_PATH目录下所有后缀为cpp的文件列表
MY_CPP_LIST := $(wildcard $(LOCAL_PATH)/*.cpp)
###观察变量 MY_CPP_LIST 的值
$(info MY_CPP_LIST=$(MY_CPP_LIST))


include $(CLEAR_VARS)

#模块名
LOCAL_MODULE := libndk

#定义LOCAL_SRC_FILES变量, 表示需要编译的源文件, 赋值方式以下三选一即可
#方式一:对MY_CPP_LIST中每一项,应用冒号后面的规则,规则是$(LOCAL_PATH)/%=%,意思是,查找所有$(LOCAL_PATH)/开头的项,并截取后面部分
LOCAL_SRC_FILES := $(MY_CPP_LIST:$(LOCAL_PATH)/%=%)
#方式二:替换每一项中的 "$(LOCAL_PATH)/" 为 ""(空)
#LOCAL_SRC_FILES := $(subst $(LOCAL_PATH)/, , $(MY_CPP_LIST))
#方式三:同模式替换,这里使用patsubst函数,替换每一项中的 "$(LOCAL_PATH)/" 为 ""
#LOCAL_SRC_FILES := $(patsubst $(LOCAL_PATH)/%, %, $(MY_CPP_LIST))


###观察变量 LOCAL_SRC_FILES 的值
$(info LOCAL_SRC_FILES=$(LOCAL_SRC_FILES))



include $(BUILD_SHARED_LIBRARY)


#########打印日志的相关用法###########
#
#$(info LOCAL_PATH=$(LOCAL_PATH))
#$(warning MY_CPP_LIST=$(MY_CPP_LIST))
#
########
#!/bin/bash
#-----------------------------------------
# 自动部署启动脚本
# 1. 检测manage tomcat进程是否存在，存在则kill掉
# 2. 将webapps/manage下文件打成tar包存放在backup目录下
# 3. 将ROOT.zip解压到webapps/目录下
# 4. 启动bin/startup.sh脚本
#-----------------------------------------
set -x
PROG_NAME=$0
ACTION=$1

usage() {
    echo "Usage: $PROG_NAME"
    echo "  start : start service in a separate window"
    exit 1
}

SERVER_DIR=/data/fund_manage/apache-tomcat-finance-manage

DEPLOY_DIR=$SERVER_DIR/webapps

LOG_DIR=$SERVER_DIR/logs

export JAVA_OPTS="-Dfile.encoding=UTF-8"

export LOGGING_CONFIG=$LOG_DIR

export CLASSPATH="$SERVER_DIR/lib/*:$DEPLOY_DIR/WEB-INF/lib/*:$DEPLOY_DIR/WEB-INF/classes:$DEPLOY_DIR/WEB-INF/conf"


current_dir=$PWD
backup_dir=$PWD/backup

#kill掉服务
kill_server(){
    PID=`ps -ef|grep finance-manage|grep -v $0|grep -v "grep"|awk '{print $2}'`
    if [ ! -n "$PID" ]; then
      echo "server is shutdown"
    else
      echo "server is running, then kill it"
      kill -9 $PID
    fi

}

#部署服务
deploy_web(){
    file_name="manage."`date +%s`".tar"
    tar -cvf $backup_dir/$file_name $DEPLOY_DIR/manage/
    rm -fr $DEPLOY_DIR/manage/common $DEPLOY_DIR/manage/WEB-INF *.jsp
    unzip -o ROOT.zip -d $DEPLOY_DIR/manage/
}

#启动前建立日志路径
pre_start(){
   if [ ! -d "$LOG_DIR" ];then
    mkdir -p $LOG_DIR
   fi
}

#启动应用
start(){
   pre_start
   cd $SERVER_DIR/bin
   exec $SERVER_DIR/bin/startup.sh
   sleep 1
   PID="$!"
   echo "service started, pid: $PID"
}

#正常关闭应用
shutdown(){
   SP_PID=`ps -ef|grep finance-manage|grep -v $0|grep -v "grep"|awk '{print $2}'`
   if [ ! -n "$SP_PID" ]; then
         echo "server is shutdown"
   else
       cd $SERVER_DIR/bin
       exec $SERVER_DIR/bin/shutdown.sh

   fi
}

#deploy操作
deploy(){
    kill_server
    deploy_web
    start
}

#stop操作
stop(){
   shutdown
   kill_server
}

#restart操作
restart(){
   stop
   start
}


case "$ACTION" in
   deploy)
      deploy
   ;;
   start)
      start
   ;;
   stop)
      stop
   ;;
   restart)
      restart
   ;;
   *)
      usage
   ;;
esac
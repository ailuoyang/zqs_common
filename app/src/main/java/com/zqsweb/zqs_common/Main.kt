package com.zqsweb.zqs_common

/*
 *   @author zhangqisheng
 *   @date 2020-05-22 15:16
 *   @description 
 */

class Main {

        fun main(args: Array<String>) {
            vars(1,2,3,4,5)  // 输出12345
        }

        val num=123_4;

        fun vars(vararg v : Int){
            for (vt in v) {
                print(vt)
            }
        }

}
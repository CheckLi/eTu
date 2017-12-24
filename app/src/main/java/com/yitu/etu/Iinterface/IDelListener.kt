package com.yitu.etu.Iinterface

/**
 *
 *@className:IDelListener
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月23日 17:55
 *
 */
interface IDelListener<T> {
    fun del(delPosition:Int,bean:T)
}
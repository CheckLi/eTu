package com.yitu.etu.eventBusItem

import android.os.Bundle

/**
 *
 *@className:Event
 *@description:
 * @author: JIAMING.LI
 * @date:2017年12月20日 11:40
 *
 */

class EventClearSuccess(val success:Boolean)
class EventRefresh(val classname:String)
class EventPlayYanHua(val play:Boolean)
class EventOpenRealTime(val bundle:Bundle?,val add:Boolean)
class EventChangSize(var height:Int)
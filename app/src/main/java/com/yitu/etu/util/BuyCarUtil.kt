package com.yitu.etu.util

import com.yitu.etu.entity.AppConstant
import com.yitu.etu.entity.BuyCar

/**
 * @className:BuyCarUtil
 * @description:
 * @author: JIAMING.LI
 * @date:2017年12月22日 21:58
 */
object BuyCarUtil {

    /**
     * 添加到购物车
     */
    @JvmStatic
    fun addBuyCar(buycar: BuyCar) {
        var buys = PrefrersUtil.getInstance().getListClass(AppConstant.PARAM_SAVE_BUY_CAR, BuyCar::class.java)
        if (buys == null || buys.size == 0) {
            buys = arrayListOf(buycar)
            PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buys)
        } else {
            val temp = arrayListOf(buycar)
            temp.clear()
            buys.forEachIndexed { index, Car ->
                if (Car.id == buycar.id) {
                    Car.count += buycar?.count
                    PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buys)
                    return
                }
            }
            temp.add(buycar)
            buys.addAll(temp)
            PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buys)
        }

    }


    /**
     * 删除一个商品
     */
    @JvmStatic
    fun cutBuyCar(buycar: BuyCar) {
        var buys = PrefrersUtil.getInstance().getListClass(AppConstant.PARAM_SAVE_BUY_CAR, BuyCar::class.java)
        if (buys == null || buys.size == 0) {
            return
        } else {
            buys.forEachIndexed { index, Car ->
                if (Car.id == buycar.id) {
                    buys.remove(Car)
                    PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buys)
                    return
                }
            }

        }
    }

    @JvmStatic
    fun removeCar(ids:String){
        if(ids.isNullOrBlank()){
            return
        }
        val id=ids.split(",")
        id?.forEach {
            cutBuyCar(BuyCar(it.toInt()))
        }
    }

    /**
     * 获取所有购物车
     */
    @JvmStatic
    fun getAllBuyCar(): ArrayList<BuyCar> {
        return PrefrersUtil.getInstance().getListClass(AppConstant.PARAM_SAVE_BUY_CAR, BuyCar::class.java)
    }

    /**
     * 保存所有购物车
     */
    @JvmStatic
    fun saveBuyCar(buycar: MutableList<BuyCar>?) {
        if (buycar != null && buycar.size > 0) {
            PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buycar)
        } else {
            PrefrersUtil.getInstance().saveValue(AppConstant.PARAM_SAVE_BUY_CAR, "[]")
        }
    }
}

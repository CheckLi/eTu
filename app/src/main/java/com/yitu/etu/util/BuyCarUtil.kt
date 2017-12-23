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
    fun addBuyCar(buycar: BuyCar) {
        var buys = PrefrersUtil.getInstance().getListClass(AppConstant.PARAM_SAVE_BUY_CAR, BuyCar::class.java)
        if (buys == null || buys.size == 0) {
            buys = arrayListOf(buycar)
        } else {
            val temp = arrayListOf(buycar)
            temp.clear()
            buys.forEachIndexed { index, Car ->
                if (Car.id != buycar.id) {
                    temp.add(buycar)
                } else {
                    Car.count++
                }
            }
            buys.addAll(temp)
        }
        PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buys)
    }


    /**
     * 删除一个商品
     */
    fun cutBuyCar(buycar: BuyCar) {
        var buys = PrefrersUtil.getInstance().getListClass(AppConstant.PARAM_SAVE_BUY_CAR, BuyCar::class.java)
        if (buys == null || buys.size == 0) {
            return
        } else {
            buys.forEachIndexed { index, Car ->
                if (Car.id == buycar.id) {
                    buys.remove(buycar)
                    PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buys)
                    return
                }
            }

        }
    }

    /**
     * 获取所有购物车
     */
    fun getAllBuyCar(): ArrayList<BuyCar> {
        return PrefrersUtil.getInstance().getListClass(AppConstant.PARAM_SAVE_BUY_CAR, BuyCar::class.java)
    }

    /**
     * 保存所有购物车
     */
    fun saveBuyCar(buycar: MutableList<BuyCar>?) {
        if (buycar != null && buycar.size > 0) {
            PrefrersUtil.getInstance().saveClass(AppConstant.PARAM_SAVE_BUY_CAR, buycar)
        }else{
            PrefrersUtil.getInstance().saveValue(AppConstant.PARAM_SAVE_BUY_CAR,"[]")
        }
    }
}

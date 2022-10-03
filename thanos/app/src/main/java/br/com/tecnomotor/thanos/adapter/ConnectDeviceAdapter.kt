package br.com.tecnomotor.thanos.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import br.com.tecnomotor.rasther.RastherDescription
import br.com.tecnomotor.rasther.RastherList
import br.com.tecnomotor.thanos.R

class ConnectDeviceAdapter(context: Context,
    private val listDevices: RastherList
) : ArrayAdapter<RastherDescription>(context,
        R.layout.devices_items, listDevices) {

    override fun getView(position: Int, viewArg: View?, parent: ViewGroup): View {
        var view = viewArg

        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.devices_items, parent, false)

            val viewHolder =
                ItemDeviceViewHolder(
                    view.findViewById(R.id.imgDevice),
                    view.findViewById(R.id.tvDeviceMac),
                    view.findViewById(R.id.tvDeviceSerialNumber)
                )

            view.setTag(viewHolder)
        }
        val device = getItem(position)

        device?.image?.let { (view?.tag as ItemDeviceViewHolder).deviceImage.setImageResource(it) }
        (view?.tag as ItemDeviceViewHolder).deviceMac.text = device?.address
        (view.tag as ItemDeviceViewHolder).deviceSerialNumber.text = device?.name

        return view
    }

    fun atualiza(dataList: RastherList) {
        this.clear()
        this.addAll(dataList)
    }

    fun getDeviceByMac(mac: String): RastherDescription? {
        for (indice in 0..count - 1) {
            val device = getItem(indice)
            if (mac == device?.address){
                return device
            }
        }
        return null
    }

    fun getAll(): RastherList {
        val lista = RastherList()
        for (indice in 0..count - 1) {
            getItem(indice)?.let{
                lista.add(it)
            }
        }
        return lista
    }

    data class ItemDeviceViewHolder (
        val deviceImage: ImageView,
        val deviceMac: TextView,
        val deviceSerialNumber: TextView
    )
}
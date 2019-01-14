package com.motolies.playmart;

/**
 * Created by motolies on 2017-02-06.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class ListViewAdapter extends BaseAdapter {
    // Adapter에 추가된 데이터를 저장하기 위한 ArrayList
    //private ArrayList<ListViewItem> listViewItemList = new ArrayList<ListViewItem>();

    private LinkedHashMap<String, ListViewItem> listViewItemList = new LinkedHashMap<String, ListViewItem>();
    private String[] mKeys;


    // ListViewAdapter의 생성자
    public ListViewAdapter() {

    }

    // Adapter에 사용되는 데이터의 개수를 리턴. : 필수 구현
    @Override
    public int getCount() {
        return listViewItemList.size();
    }


    // 지정한 위치(position)에 있는 데이터와 관계된 아이템(row)의 ID를 리턴. : 필수 구현
    @Override
    public long getItemId(int position) {
        return position;
    }

    // 지정한 위치(position)에 있는 데이터 리턴 : 필수 구현
    @Override
    public Object getItem(int position) {
        return listViewItemList.get(mKeys[position]);
    }


    // position에 위치한 데이터를 화면에 출력하는데 사용될 View를 리턴. : 필수 구현
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final int pos = position;
        final Context context = parent.getContext();

        TextView itemNameTextView = null;
        TextView itemUnitPriceTextView = null;
        TextView itemUnitTextView = null;
        TextView itemTotalPriceTextView = null;
        CustomHolder holder = null;

        // "listview_item" Layout을 inflate하여 convertView 참조 획득.
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.list_item, parent, false);

            itemNameTextView = (TextView) convertView.findViewById(R.id.itemName);
            itemUnitPriceTextView = (TextView) convertView.findViewById(R.id.itemUnitPrice);
            itemUnitTextView = (TextView) convertView.findViewById(R.id.itemUnit);
            itemTotalPriceTextView = (TextView) convertView.findViewById(R.id.itemTotalPrice);

            // 홀더 생성 및 Tag로 등록
            holder = new CustomHolder();
            holder.m_itemNameTextView = itemNameTextView;
            holder.m_itemUnitPriceTextView = itemUnitPriceTextView;
            holder.m_itemUnitTextView = itemUnitTextView;
            holder.m_itemTotalPriceTextView = itemTotalPriceTextView;
            convertView.setTag(holder);
        } else {
            holder = (CustomHolder) convertView.getTag();

            itemNameTextView = holder.m_itemNameTextView;
            itemUnitPriceTextView = holder.m_itemUnitPriceTextView;
            itemUnitTextView = holder.m_itemUnitTextView;
            itemTotalPriceTextView = holder.m_itemTotalPriceTextView;

        }

        // Data Set(listViewItemList)에서 position에 위치한 데이터 참조 획득
        String key = mKeys[position];
        ListViewItem listViewItem = (ListViewItem) getItem(position);

        // 아이템 내 각 위젯에 데이터 반영
        itemNameTextView.setText(listViewItem.getName());
        itemUnitPriceTextView.setText(String.format("개당 %,d원", listViewItem.getUnitPrice()));
        itemUnitTextView.setText(String.format("%,d개", listViewItem.getUnit()));
        itemTotalPriceTextView.setText(String.format("총 %,d원", listViewItem.getTotalPrice()));


        return convertView;
    }


    // 아이템 데이터 추가를 위한 함수. 개발자가 원하는대로 작성 가능.
    public void addItem(String barCode, String name, int unitPrice) {
        // dictionary 로 구조 변경시 일단 key가 있는지 찾고, 있으면 값을 1 더하고, 없으면 1을 할당한다

        if (listViewItemList.containsKey(barCode)) {
            // 있으면 값에 1을 더한다
            listViewItemList.get(barCode).setUnit(listViewItemList.get(barCode).getUnit() + 1);
        } else {
            // 없으면 새로 리스트아이템을 만들고 1을 더한다.
            ListViewItem item = new ListViewItem();
            item.setName(name);
            item.setUnitPrice(unitPrice);
            item.setUnit(1);

            listViewItemList.put(barCode, item);
            mKeys = listViewItemList.keySet().toArray(new String[listViewItemList.size()]);
        }


    }


    public void removeItem(int position) {
        // dictionary 로 구조 변경시 일단 key가 있는지 찾고, 있으면 하나씩 제거하고, 1가나 남으면 리스트에서 제거한다
        listViewItemList.remove(position);
    }

    public void removeItem(String barCode) {
        // dictionary 로 구조 변경시 일단 key가 있는지 찾고, 있으면 하나씩 제거하고, 1가나 남으면 리스트에서 제거한다
        if (listViewItemList.containsKey(barCode)) {

            ListViewItem item = listViewItemList.get(barCode);
            if (item.getUnit() > 1) {
                listViewItemList.get(barCode).setUnit(listViewItemList.get(barCode).getUnit() - 1);
            } else {
                listViewItemList.remove(barCode);
                mKeys = listViewItemList.keySet().toArray(new String[listViewItemList.size()]);
            }

        }


    }

    private class CustomHolder {
        TextView m_itemNameTextView;
        TextView m_itemUnitPriceTextView;
        TextView m_itemUnitTextView;
        TextView m_itemTotalPriceTextView;

    }
}
package com.ning.home_admin.bean.ov;

import com.ning.home_admin.bean.Details;
import com.ning.home_admin.bean.ItemBig;
import com.ning.home_admin.bean.ItemSmall;
import lombok.Data;

import java.util.List;

@Data
public class ShowAllDetails {
    Details details;
    List<ItemSmall> smallList;
    List<ItemBig> bigList;
}

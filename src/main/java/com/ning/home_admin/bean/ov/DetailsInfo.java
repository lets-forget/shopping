package com.ning.home_admin.bean.ov;


import com.ning.home_admin.bean.Details;
import com.ning.home_admin.bean.ItemSmall;
import com.ning.home_admin.bean.Order;
import lombok.Data;

import java.io.Serializable;

@Data
public class DetailsInfo implements Serializable {

    ItemSmall itemSmall;

    Details details;

    Order order;

    Integer count;

    Double subtotal;
}

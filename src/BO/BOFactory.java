package BO;

import BO.Custom.Impl.*;

public class BOFactory {

    private static BOFactory boFactory;

    private BOFactory(){

    }

    public static BOFactory getBoFactory(){
        if (boFactory == null){
            boFactory = new BOFactory();
        }
        return boFactory;
    }

    public enum BOTypes{
        CUSTOMER,ITEM,LOGIN,PLACEORDER,SEARCHORDER
    }

    public SuperBO getBO(BOTypes types){
        switch (types) {
            case CUSTOMER:
                return new CustomerBOImpl();
            case ITEM:
                return new ItemBOImpl();
            case LOGIN:
                return new LoginBOImpl();
            case PLACEORDER:
                return new PlaceOrderBOImpl();
            case SEARCHORDER:
                return new SearchOrderBOImpl();
            default:
                return null;
        }
    }
}

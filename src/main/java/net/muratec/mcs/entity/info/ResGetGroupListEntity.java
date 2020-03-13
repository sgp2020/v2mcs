package net.muratec.mcs.entity.info;

import java.util.ArrayList;
import java.util.List;

import net.muratec.mcs.entity.common.AjaxDataTablesResBaseEntity;

public class ResGetGroupListEntity extends AjaxDataTablesResBaseEntity {

    public GroupListEntity body1 = new GroupListEntity();
    public List<GroupPortListEntity> body2 = new ArrayList<GroupPortListEntity>();

}

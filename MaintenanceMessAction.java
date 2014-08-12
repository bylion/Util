package com.aligntech.pluto.a3.action;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.aligntech.pluto.common.system.Pluto;
import com.aligntech.pluto.common.util.exception.PlutoException;
import com.aligntech.pluto.core.annotations.Action;
import com.aligntech.pluto.core.info.RegionConfigurationInfo;
import com.aligntech.pluto.core.info.RegionParameterEnum;
import com.aligntech.pluto.core.service.RegionConfigurationService;
import com.aligntech.pluto.core.service.RegionCountryService;
import com.aligntech.pluto.web.action.WebAction;

@Action
public class MaintenanceMessAction extends WebAction {
    protected static final long serialVersionUID = 1;
    private String maintenanceMess = "";
    /**
     * @deprecated
     */
    private String country;
    /**
     * @deprecated
     */
    public String getCountry() {
        return country;
    }
    /**
     * @deprecated
     */
    public void setCountry(String country) {
        this.country = country;
    }
    @Autowired
    private RegionConfigurationService regionConfigurationService;
    @Autowired
    private RegionCountryService regionCountryService;
    public void setRegionCountryService(RegionCountryService regionCountryService) {
        this.regionCountryService = regionCountryService;
    }
    public void setRegionConfigurationService(RegionConfigurationService regionConfigurationService) {
        this.regionConfigurationService = regionConfigurationService;
    }
    public String getMaintenanceMess() {
        return maintenanceMess;
    }
    public void setMaintenanceMess(String maintenanceMess) {
        this.maintenanceMess = maintenanceMess;
    }
    public String execute() throws Exception {
        Map<String, Object> config = Pluto.config().getMap();
        if (config.get("DOWNTIME_MSG_EXPIRATION_TIME") instanceof String
                && config.get("com.aligntech.pluto.common.support.maintenanceDowntimePeriod") instanceof String) {
            String downtimeStr = (String) config.get("DOWNTIME_MSG_EXPIRATION_TIME");
            String downtimeMSg = (String) config.get("com.aligntech.pluto.common.support.maintenanceDowntimePeriod");
            Date end = transferStrintoDate(downtimeStr);
            Date d = new Date();
            if (d.compareTo(end) < 0) {
                maintenanceMess = downtimeMSg;
            }
        } else {
            maintenanceMess += "setting error;  date format invalid";
        }
        return SUCCESS;
    }

    /**
     * @deprecated
     */
    public void prepareMessage() {
        RegionConfigurationInfo regionConfigurationInfo;
        regionConfigurationInfo = regionConfigurationService
                .getRegionConfigurationInfo(RegionParameterEnum.MAINTENANCE_DATE_TIME);
        for (Map.Entry<RegionConfigurationInfo.CountryKey, String> e : regionConfigurationInfo.getCountryParameters()
                .entrySet()) {
            if (country != null && country.equals(e.getKey().getCountryInfo().getCountryCode())) {
                maintenanceMess += e.getValue();
                return;
            }
        }
        for (Map.Entry<RegionConfigurationInfo.RegionKey, String> e : regionConfigurationInfo.getRegionParameters()
                .entrySet()) {
            String re = null;
            try {
                re = regionCountryService.getRegionForCountry(country);
            } catch (PlutoException e1) {
                LOG.error("Can't getRegionForCountry: " + country + ".", e1);
                e1.printStackTrace();
            }
            if (null != re && re.equals(e.getKey().getRegion())) {
                maintenanceMess += e.getValue();
            }
        }
    }
    public Date transferStrintoDate(String input) {
        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date t = null;
        try {
            t = ft.parse(input);
            System.out.println(t);
        } catch (ParseException e) {
            LOG.error("Can't load date from RegionParameter.MAINTENANCE_DATE_TIME: " + input + ".", e);
        }
        return t;
    }
}

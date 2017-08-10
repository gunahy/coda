package main.system;

/**
 * Created by Kobzar on 16.06.2017.
 */
public enum CompanyProperties {
    //TODO добавить запись для объектов типа "DC=ms11,DC=biz"
    MS11("ms11.biz", "akagi.ms11.biz", "Мостострой - 11 АО"),
    MO36("mo36.ms11.biz", "mc1.mo36.ms11.biz", "Мостоотряд - 36"),
    MO15("mo15.ms11.biz", "mo15-1.mo15.ms11.biz", "Мостоотряд - 15"),
    MO87("mo87.ms11.biz", "mo87-2.mo87.ms11.biz", "Мостоотряд - 87"),
    MO29("mo29.ms11.biz", "srvml350.mo29.ms11.biz", "Мостоотряд - 29"),
    SU("su.ms11.biz", "su_1c.su.ms11.biz", "СУ Мостострой-11"),
    DSU("dsu.ms11.biz", "sayuki.dsu.ms11.biz", "ДСУ Мостострой-11");

    private final String searchBase;
    private final String serverName;
    private final String companyName;

    CompanyProperties(String searchBase, String serverName, String companyName) {
        this.searchBase = searchBase;
        this.serverName = serverName;
        this.companyName = companyName;
    }

    public String getSearchBase() {
        return searchBase;
    }

    public String getServerName() {
        return serverName;
    }

    public String getCompanyName() {
        return companyName;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

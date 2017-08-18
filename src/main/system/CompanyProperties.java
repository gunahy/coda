package main.system;

/**
 * Created by Kobzar on 16.06.2017.
 */
public enum CompanyProperties {
    //TODO добавить запись для объектов типа "DC=ms11,DC=biz"
    MS11("ms11.biz", "akagi.ms11.biz", "Мостострой - 11 АО", ",DC=ms11,DC=biz"),
    MO36("mo36.ms11.biz", "mc1.mo36.ms11.biz", "Мостоотряд - 36", ",DC=mo36,DC=ms11,DC=biz"),
    MO15("mo15.ms11.biz", "mo15-1.mo15.ms11.biz", "Мостоотряд - 15", ",DC=mo15,DC=ms11,DC=biz"),
    MO87("mo87.ms11.biz", "mo87-2.mo87.ms11.biz", "Мостоотряд - 87", ",DC=mo87,DC=ms11,DC=biz"),
    MO29("mo29.ms11.biz", "srvml350.mo29.ms11.biz", "Мостоотряд - 29", ",DC=mo29,DC=ms11,DC=biz"),
    SU("su.ms11.biz", "su_1c.su.ms11.biz", "СУ Мостострой-11", ",DC=su,DC=ms11,DC=biz"),
    DSU("dsu.ms11.biz", "sayuki.dsu.ms11.biz", "ДСУ Мостострой-11", ",DC=dsu,DC=ms11,DC=biz");

    private final String searchBase;
    private final String serverName;
    private final String companyName;
    private final String domainSuffix;

    CompanyProperties(String searchBase, String serverName, String companyName, String domainSuffix) {
        this.searchBase = searchBase;
        this.serverName = serverName;
        this.companyName = companyName;
        this.domainSuffix = domainSuffix;
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

    public String getDomainSuffix() {
        return domainSuffix;
    }

    @Override
    public String toString() {
        return name().toLowerCase();
    }
}

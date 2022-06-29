/**
 * @author Aglet
 * @create 2022/6/29 17:39
 */
module vary {

    exports org.luvsa.vary;

    uses org.luvsa.vary.Factory;
    uses org.luvsa.vary.bool.BProvider;
    uses org.luvsa.vary.chrono.CProvider;
    uses org.luvsa.vary.chrono.zoned.ZProvider;
    uses org.luvsa.vary.date.DProvider;
    uses org.luvsa.vary.instant.IProvider;
    uses org.luvsa.vary.local.LProvider;
    uses org.luvsa.vary.number.NProvider;
    uses org.luvsa.vary.other.OProvider;
    uses org.luvsa.vary.string.SProvider;
    uses org.luvsa.vary.temporal.TProvider;

    provides org.luvsa.vary.Factory with
            org.luvsa.vary.bool.BFactory,
            org.luvsa.vary.chrono.CFactory,
            org.luvsa.vary.chrono.zoned.ZFactory,
            org.luvsa.vary.date.DFactory,
            org.luvsa.vary.instant.IFactory,
            org.luvsa.vary.local.LFactory,
            org.luvsa.vary.number.NFactory,
            org.luvsa.vary.other.OFactory,
            org.luvsa.vary.string.SFactory,
            org.luvsa.vary.temporal.TFactory;

    provides org.luvsa.vary.string.SProvider with
            org.luvsa.vary.string.ToDate;
}
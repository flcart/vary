/**
 * @author Aglet
 * @create 2022/6/29 17:39
 */
module vary {

    exports org.luvsa.vary;
    exports org.luvsa.vary.bool;
    exports org.luvsa.vary.chrono;
    exports org.luvsa.vary.chrono.zoned;
    exports org.luvsa.vary.date;
    exports org.luvsa.vary.instant;
    exports org.luvsa.vary.local;
    exports org.luvsa.vary.number;
    exports org.luvsa.vary.other;
    exports org.luvsa.vary.string;
    exports org.luvsa.vary.string.array;
    exports org.luvsa.vary.temporal;

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
    uses org.luvsa.vary.string.array.AProvider;
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
            org.luvsa.vary.string.array.AFactory,
            org.luvsa.vary.temporal.TFactory;

    provides org.luvsa.vary.bool.BProvider with
            org.luvsa.vary.bool.ToInteger,
            org.luvsa.vary.bool.ToNumber,
            org.luvsa.vary.bool.ToString;

    provides org.luvsa.vary.chrono.CProvider with
            org.luvsa.vary.chrono.ToZoned,
            org.luvsa.vary.chrono.ToOther;

    provides org.luvsa.vary.chrono.zoned.ZProvider with
            org.luvsa.vary.chrono.zoned.ToInstant,
            org.luvsa.vary.chrono.zoned.ToOther;

    provides org.luvsa.vary.date.DProvider with
            org.luvsa.vary.date.ToInstant,
            org.luvsa.vary.date.ToZoned,
            org.luvsa.vary.date.ToLocal,
            org.luvsa.vary.date.ToLong,
            org.luvsa.vary.date.ToString;

    provides org.luvsa.vary.instant.IProvider with
            org.luvsa.vary.instant.ToZoned,
            org.luvsa.vary.instant.ToDate,
            org.luvsa.vary.instant.ToLocal,
            org.luvsa.vary.instant.ToLong;

    provides org.luvsa.vary.local.LProvider with
            org.luvsa.vary.local.ToChrono,
            org.luvsa.vary.local.ToOther,
            org.luvsa.vary.local.ToString;

    provides org.luvsa.vary.number.NProvider with
            org.luvsa.vary.number.ToBool,
            org.luvsa.vary.number.ToByte,
            org.luvsa.vary.number.ToChar,
            org.luvsa.vary.number.ToDate,
            org.luvsa.vary.number.ToDouble,
            org.luvsa.vary.number.ToFloat,
            org.luvsa.vary.number.ToInstant,
            org.luvsa.vary.number.ToInteger,
            org.luvsa.vary.number.ToLocal,
            org.luvsa.vary.number.ToLong,
            org.luvsa.vary.number.ToShort,
            org.luvsa.vary.number.ToString;

    provides org.luvsa.vary.other.OProvider with
            org.luvsa.vary.other.ToMap,
            org.luvsa.vary.proxy.DynamicProxy;

    provides org.luvsa.vary.string.SProvider with
            org.luvsa.vary.string.ToBool,
            org.luvsa.vary.string.ToDate,
            org.luvsa.vary.string.ToDecimal,
            org.luvsa.vary.string.ToLocalDate,
            org.luvsa.vary.string.ToLocalDateTime,
            org.luvsa.vary.string.ToLocalTime,
            org.luvsa.vary.string.ToNumber;

    provides org.luvsa.vary.string.array.AProvider with
            org.luvsa.vary.string.array.ToList;

    provides org.luvsa.vary.temporal.TProvider with
            org.luvsa.vary.temporal.ToLocalDate,
            org.luvsa.vary.temporal.ToLocalDateTime,
            org.luvsa.vary.temporal.ToLocalTime;
}
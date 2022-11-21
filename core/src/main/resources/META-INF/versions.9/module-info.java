/**
 * 数据转换的工具实践，
 *
 * @author Aglet
 * @create 2022/6/29 17:39
 */
module vary {

    exports org.luvsa.exception;
    exports org.luvsa.lang;
    exports org.luvsa.reflect;
    exports org.luvsa.vary;
    exports org.luvsa.vary.array;
    exports org.luvsa.vary.bool;
    exports org.luvsa.vary.chrono;
    exports org.luvsa.vary.date;
    exports org.luvsa.vary.instant;
    exports org.luvsa.vary.local;
    exports org.luvsa.vary.number;
    exports org.luvsa.vary.other;
    exports org.luvsa.vary.proxy;
    exports org.luvsa.vary.string;
    exports org.luvsa.vary.string.array;
    exports org.luvsa.vary.zone;

    uses org.luvsa.vary.Factory;
    uses org.luvsa.vary.array.Provider;
    uses org.luvsa.vary.bool.Provider;
    uses org.luvsa.vary.chrono.Provider;
    uses org.luvsa.vary.date.Provider;
    uses org.luvsa.vary.instant.Provider;
    uses org.luvsa.vary.local.Provider;
    uses org.luvsa.vary.number.Provider;
    uses org.luvsa.vary.other.Provider;
    uses org.luvsa.vary.string.Provider;
    uses org.luvsa.vary.string.array.Provider;
    uses org.luvsa.vary.zone.Provider;
    uses org.luvsa.vary.Vary;
    uses org.luvsa.lang.Holder;

    provides org.luvsa.vary.Vary with
            org.luvsa.vary.DefaultVary;

    provides org.luvsa.vary.Factory with
            org.luvsa.vary.bool.Factory,
            org.luvsa.vary.chrono.Factory,
            org.luvsa.vary.date.Factory,
            org.luvsa.vary.instant.Factory,
            org.luvsa.vary.local.Factory,
            org.luvsa.vary.number.Factory,
            org.luvsa.vary.other.Factory,
            org.luvsa.vary.string.Factory,
            org.luvsa.vary.string.array.Factory,
            org.luvsa.vary.zone.Factory;

    provides org.luvsa.vary.bool.Provider with
            org.luvsa.vary.bool.ToInteger,
            org.luvsa.vary.bool.ToNumber,
            org.luvsa.vary.bool.ToString;

    provides org.luvsa.vary.chrono.Provider with
            org.luvsa.vary.chrono.ToZoned,
            org.luvsa.vary.chrono.ToOther;

    provides org.luvsa.vary.date.Provider with
            org.luvsa.vary.date.ToInstant,
            org.luvsa.vary.date.ToZoned,
            org.luvsa.vary.date.ToLocal,
            org.luvsa.vary.date.ToLong,
            org.luvsa.vary.date.ToString;

    provides org.luvsa.vary.instant.Provider with
            org.luvsa.vary.instant.ToZoned,
            org.luvsa.vary.instant.ToDate,
            org.luvsa.vary.instant.ToLocal,
            org.luvsa.vary.instant.ToLong;

    provides org.luvsa.vary.local.Provider with
            org.luvsa.vary.local.ToChrono,
            org.luvsa.vary.local.ToOther,
            org.luvsa.vary.local.ToString,
            org.luvsa.vary.local.ToZoned;

    provides org.luvsa.vary.number.Provider with
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

    provides org.luvsa.vary.other.Provider with
            org.luvsa.vary.other.ToMap,
            org.luvsa.vary.proxy.DynamicProxy;

    provides org.luvsa.vary.string.Provider with
            org.luvsa.vary.string.ToBool,
            org.luvsa.vary.string.ToChars,
            org.luvsa.vary.string.ToDate,
            org.luvsa.vary.string.ToDecimal,
            org.luvsa.vary.string.ToLocalDate,
            org.luvsa.vary.string.ToLocalDateTime,
            org.luvsa.vary.string.ToLocalTime,
            org.luvsa.vary.string.ToNumber;

    provides org.luvsa.vary.string.array.Provider with
            org.luvsa.vary.string.array.ToList;

    provides org.luvsa.vary.zone.Provider with
            org.luvsa.vary.zone.ToInstant,
            org.luvsa.vary.zone.ToLocalDate,
            org.luvsa.vary.zone.ToLocalDateTime,
            org.luvsa.vary.zone.ToLocalTime,
            org.luvsa.vary.zone.ToOther;
}
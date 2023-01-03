/**
 * @author Aglet
 * @create 2023/1/3 9:10
 */
module vary.struct {

    requires lombok;
    requires org.mapstruct;
    requires spring.beans;
    requires spring.context;
    requires spring.core;
    requires vary.core;

    exports org.luvsa.vary;

    uses org.luvsa.vary.StructVary;

}
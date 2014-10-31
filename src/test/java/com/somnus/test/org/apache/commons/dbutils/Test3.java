package com.somnus.test.org.apache.commons.dbutils;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.MapListHandler;

public class Test3 {

	private static Connection conn;

	public static Connection getConnection() {
		// String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=172.24.7.189)(PORT=1521))(LOAD_BALANCE=yes)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=iovdb)))";
		String url = "jdbc:oracle:thin:@(DESCRIPTION=(ADDRESS=(PROTOCOL=TCP)(HOST=172.24.176.110)(PORT=1521))(LOAD_BALANCE=yes)(CONNECT_DATA=(SERVER=DEDICATED)(SERVICE_NAME=orcl)))";
		String driverClassName = "oracle.jdbc.driver.OracleDriver";
		String username = "foton";
		String password = "123456";
		Connection conn = null;
		DbUtils.loadDriver(driverClassName);
		try {
			conn = DriverManager.getConnection(url, username, password);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return conn;
	}

	public static void main(String[] args) {
		List<String[]> resource = initData();

		conn = getConnection();
		QueryRunner qr = new QueryRunner();
		try {
			conn.setAutoCommit(false);
			for (String[] s : resource) {
				List<Map<String, Object>> lm = qr.query(conn, "select * from IOV_VL_ORDER t where t.vin = ?", new MapListHandler(), s[0]);
				if (lm != null && lm.size() > 0) {
					Long orderId = ((BigDecimal) lm.get(0).get("ID")).longValue();
					String orderNum = (String) lm.get(0).get("ORDER_NUM");
					String vin = s[0];
					String endDate = "2013-" + s[1].replaceAll("月", "-").replaceAll("日", "");
					System.out.println(orderId + "-" + orderNum + "-" + vin + "-" + endDate);
					String sql1 = "insert into iov_vl_order_operate (id, content, operate_time, operator, created, modified, cbm_mag_company_id, iov_vl_order_id) values (seq_iov_vl_order_operate.nextval, '操作类型：[到达确认]操作人备注:{运单[" + orderNum + "]-[" + vin + "]到达确认}', sysdate, '物流超级管理员', sysdate, sysdate, 1020, " + orderId + ")";
					System.out.println(sql1);
					String sql2 = "update IOV_VL_ORDER t set t.end_date = to_date('" + endDate + "','yyyy-mm-dd'),t.status=4,t.modified=sysdate where vin = '" + vin + "'";
					System.out.println(sql2);
					qr.update(conn, sql1);
					qr.update(conn, sql2);
					conn.commit();
				}
			}
		} catch (SQLException e) {
			try {
				DbUtils.rollback(conn);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				DbUtils.close(conn);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private static List<String[]> initData() {
		List<String[]> resource = new ArrayList<String[]>();
		resource.add(new String[] { "LVAV2MBB3DC030112", "11月16日" });
		resource.add(new String[] { "LVAV2MBBXDC030107", "11月16日" });
		resource.add(new String[] { "LVAV2MBB0DC030472", "11月16日" });
		resource.add(new String[] { "LVAV2MBB1DC030125", "11月16日" });
		resource.add(new String[] { "LVAV2MBB8DC030476", "11月16日" });
		resource.add(new String[] { "LVAV2MBB5DC030113", "11月16日" });
		resource.add(new String[] { "LVAV2MBB2DC030537", "11月16日" });
		resource.add(new String[] { "LVAV2MBB6DC030122", "11月16日" });
		resource.add(new String[] { "LVAV2MBB5DC029138", "11月16日" });
		resource.add(new String[] { "LVAV2MBB8DC028632", "11月16日" });
		resource.add(new String[] { "LVAV2MBB4DC030152", "11月16日" });
		resource.add(new String[] { "LVAV2MBB2DC030151", "11月16日" });
		resource.add(new String[] { "LVAV2MBB2DC030022", "11月16日" });
		resource.add(new String[] { "LVAV2MWB5DC029683", "11月17日" });
		resource.add(new String[] { "LVAV2MBB9DC030311", "11月17日" });
		resource.add(new String[] { "LVAV2MBBXDC029426", "11月9日" });
		resource.add(new String[] { "LVAV2MBB9DC029515", "11月9日" });
		resource.add(new String[] { "LVAV2MBB0DC029810", "11月9日" });
		resource.add(new String[] { "LVAV2MWB7DC029264", "11月17日" });
		resource.add(new String[] { "LVAV2MWB9DC029265", "11月17日" });
		resource.add(new String[] { "LVAV2MWB3DC029262", "11月17日" });
		resource.add(new String[] { "LVAV2MWB1DC029261", "11月17日" });
		resource.add(new String[] { "LVAV2MWB5DC029263", "11月17日" });
		resource.add(new String[] { "LVAV2MWB0DC029736", "11月17日" });
		resource.add(new String[] { "LVAV2MBBXDC029779", "11月11日" });
		resource.add(new String[] { "LVAV2MBB1DC029735", "11月11日" });
		resource.add(new String[] { "LVAV2MBB6DC029732", "11月11日" });
		resource.add(new String[] { "LVAV2MBB0DC029757", "11月11日" });
		resource.add(new String[] { "LVAV2MBB5DC029754", "11月11日" });
		resource.add(new String[] { "LVAV2MBBXDC029734", "11月11日" });
		resource.add(new String[] { "LVAV2MBB6DC029777", "11月11日" });
		resource.add(new String[] { "LVAV2MBB2DC029758", "11月11日" });
		resource.add(new String[] { "LVAV2MBB1DC029752", "11月11日" });
		resource.add(new String[] { "LVAV2MBB4DC029759", "11月11日" });
		resource.add(new String[] { "LVAV2MBB8DC029778", "11月11日" });
		resource.add(new String[] { "LVAV2MBB7DC029755", "11月11日" });
		resource.add(new String[] { "LVAV2MBBXDC029751", "11月11日" });
		resource.add(new String[] { "LVAV2MBB9DC029756", "11月11日" });
		resource.add(new String[] { "LVAV2MBB3DC029753", "11月11日" });
		resource.add(new String[] { "LVAV2MBB6DC026586", "9月30日" });
		resource.add(new String[] { "LVAV2MBB9DC024637", "10月4日" });
		resource.add(new String[] { "LVAV2MBB5DC025509", "10月4日" });
		resource.add(new String[] { "LVAV2MBB2DC024835", "10月4日" });
		resource.add(new String[] { "LVAV2MBB0DC024834", "10月4日" });
		resource.add(new String[] { "LVAV2MBB6DC025860", "10月4日" });
		resource.add(new String[] { "LVAV2MBB0DC026258", "10月4日" });
		resource.add(new String[] { "LVAV2MBB9DC026257", "10月4日" });
		resource.add(new String[] { "LVAV2MBB5DC026496", "10月4日" });
		resource.add(new String[] { "LVAV2MBB7DC025205", "9月30日" });
		resource.add(new String[] { "LVAV2MBB6DC026491", "10月1日" });
		resource.add(new String[] { "LVAV2MBB9DC026842", "10月1日" });
		resource.add(new String[] { "LVAV2MBB4DC026733", "10月2日" });
		resource.add(new String[] { "LVAV2MBB0DC026731", "10月2日" });
		resource.add(new String[] { "LVAV2MBB9DC026730", "10月2日" });
		resource.add(new String[] { "LVAV2MAB5DC026001", "9月30日" });
		resource.add(new String[] { "LVAV2MAB0DC025998", "9月30日" });
		resource.add(new String[] { "LVAV2MAB7DC026002", "9月30日" });
		resource.add(new String[] { "LVAV2MAB3DC026000", "9月30日" });
		resource.add(new String[] { "LVAV2MAB9DC025997", "9月30日" });
		resource.add(new String[] { "LVAV2MBB4DC023976", "9月11日" });
		resource.add(new String[] { "LVAV2MBB1DC023949", "9月1日" });
		resource.add(new String[] { "LVAV2MBB6DC022568", "8月31日" });
		resource.add(new String[] { "LVAV2MBB1DC023529", "8月31日" });
		resource.add(new String[] { "LVAV2MBB1DC022980", "8月31日" });
		resource.add(new String[] { "LVAV2MBB7DC023471", "8月31日" });
		resource.add(new String[] { "LVAV2MBB8DC023530", "8月31日" });
		resource.add(new String[] { "LVAV2MBB0DC023585", "8月31日" });
		resource.add(new String[] { "LVAV2MBB5DC023808", "8月31日" });
		resource.add(new String[] { "LVAV2MBB7DC023888", "8月31日" });
		resource.add(new String[] { "LVAV2MBB9DC023889", "8月31日" });
		resource.add(new String[] { "LVAV2MBB0DC023845", "8月31日" });
		resource.add(new String[] { "LVAV2MBB7DC023955", "8月31日" });
		resource.add(new String[] { "LVAV2MBB8DC023804", "8月31日" });
		resource.add(new String[] { "LVAV2MBB7DC023969", "8月31日" });
		resource.add(new String[] { "LVAV2MBB7DC023809", "8月31日" });
		resource.add(new String[] { "LVAV2MBB9DC024038", "8月28日" });
		resource.add(new String[] { "LVAV2MBB4DC023542", "8月27日" });
		resource.add(new String[] { "LVAV2MBB5DC023498", "8月30日" });
		resource.add(new String[] { "LVAV2MBB3DC023175", "8月26日" });
		resource.add(new String[] { "LVAV2MBB1DC023658", "8月26日" });
		resource.add(new String[] { "LVAV2MBB3DC022351", "8月25日" });
		resource.add(new String[] { "LVAV2MBB1DC021957", "8月25日" });
		resource.add(new String[] { "LVAV2MBB6DC023557", "8月25日" });
		resource.add(new String[] { "LVAV2MBB6DC022277", "8月25日" });
		resource.add(new String[] { "LVAV2MBB0DC023389", "8月25日" });
		resource.add(new String[] { "LVAV2MBB5DC023565", "8月25日" });
		resource.add(new String[] { "LVAV2MBB3DC023693", "8月25日" });
		resource.add(new String[] { "LVAV2MBB1DC023692", "8月25日" });
		resource.add(new String[] { "LVAV2MBB3DC023533", "8月26日" });
		resource.add(new String[] { "LVAV2MBB3DC023161", "8月26日" });
		resource.add(new String[] { "LVAV2MBB8DC023172", "8月26日" });
		resource.add(new String[] { "LVAV2MBB5DC023517", "8月26日" });
		resource.add(new String[] { "LVAV2MBB9DC023519", "8月26日" });
		resource.add(new String[] { "LVAV2MBB1DC023532", "8月26日" });
		resource.add(new String[] { "LVAV2MBB5DC023534", "8月26日" });
		resource.add(new String[] { "LVAV2MBB4DC023511", "8月26日" });
		resource.add(new String[] { "LVAV2MBBXDC023772", "8月26日" });
		resource.add(new String[] { "LVAV2MBB1DC023773", "8月26日" });
		resource.add(new String[] { "LVAV2MBBXDC023769", "8月26日" });
		resource.add(new String[] { "LVAV2MBB8DC023768", "8月26日" });
		resource.add(new String[] { "LVAV2MBB7DC023762", "8月26日" });
		resource.add(new String[] { "LVAV2MBB9DC023763", "8月26日" });
		resource.add(new String[] { "LVAV2MBB4DC023766", "8月26日" });
		resource.add(new String[] { "LVAV2MBB6DC023770", "8月26日" });
		resource.add(new String[] { "LVAV2MBB4DC019877", "8月25日" });
		resource.add(new String[] { "LVAV2MBBXDC023559", "8月25日" });
		resource.add(new String[] { "LVAV2MBB9DC023388", "8月25日" });
		resource.add(new String[] { "LVAV2MBB9DC023567", "8月25日" });
		resource.add(new String[] { "LVAV2MBB7DC023566", "8月25日" });
		resource.add(new String[] { "LVAV2MBB9DC023357", "8月25日" });
		resource.add(new String[] { "LVAV2MBB7DC023356", "8月25日" });
		resource.add(new String[] { "LVAV2MBB2DC021501", "8月25日" });
		resource.add(new String[] { "LVAV2MBB8DC023558", "8月25日" });
		resource.add(new String[] { "LVAV2MBB4DC021502", "8月25日" });
		resource.add(new String[] { "LVAV2MBB6DC023672", "8月25日" });
		resource.add(new String[] { "LVAV2MBB0DC022694", "8月25日" });
		resource.add(new String[] { "LVAV2MBBXDC022962", "8月25日" });
		resource.add(new String[] { "LVAV2MBB6DC022134", "8月25日" });
		resource.add(new String[] { "LVAV2MBB0DC022128", "8月25日" });
		resource.add(new String[] { "LVAV2MAB7DC023410", "8月24日" });
		resource.add(new String[] { "LVAV2MBB7DC023051", "8月24日" });
		resource.add(new String[] { "LVAV2MBB9DC023150", "8月24日" });
		resource.add(new String[] { "LVAV2MBBXDC023089", "8月24日" });
		resource.add(new String[] { "LVAV2MBB4DC022889", "8月24日" });
		resource.add(new String[] { "LVAV2MBB2DC023149", "8月24日" });
		resource.add(new String[] { "LVAV2MBB2DC022647", "8月24日" });
		resource.add(new String[] { "LVAV2MBB0DC023148", "8月24日" });
		resource.add(new String[] { "LVAV2MBB5DC022674", "8月27日" });
		resource.add(new String[] { "LVAV2MBB5DC022853", "8月27日" });
		resource.add(new String[] { "LVAV2MBB8DC022703", "8月27日" });
		resource.add(new String[] { "LVAV2MBB6DC022702", "8月27日" });
		resource.add(new String[] { "LVAV2MBB7DC022644", "8月27日" });
		resource.add(new String[] { "LVAV2MBB7DC022854", "8月27日" });
		resource.add(new String[] { "LVAV2MBB9DC022788", "8月27日" });
		resource.add(new String[] { "LVAV2MBB1DC022784", "8月27日" });
		resource.add(new String[] { "LVAV2MBB5DC021234", "8月22日" });
		resource.add(new String[] { "LVAV2MBB6DC022666", "8月21日" });
		resource.add(new String[] { "LVAV2MBBXDC022735", "8月21日" });
		resource.add(new String[] { "LVAV2MBB8DC022698", "8月21日" });
		resource.add(new String[] { "LVAV2MBB2DC022714", "8月21日" });
		resource.add(new String[] { "LVAV2MBB3DC021796", "8月21日" });
		resource.add(new String[] { "LVAV2MBB5DC021797", "8月21日" });
		resource.add(new String[] { "LVAV2MBB9DC021737", "8月23日" });
		resource.add(new String[] { "LVAV2MBB7DC021736", "8月23日" });
		resource.add(new String[] { "LVAV2MWBXDC022776", "8月21日" });
		resource.add(new String[] { "LVAV2MWB3DC022778", "8月21日" });
		resource.add(new String[] { "LVAV2MWB6DC022824", "8月21日" });
		resource.add(new String[] { "LVAV2MWB1DC022813", "8月21日" });
		resource.add(new String[] { "LVAV2MWB0DC022785", "8月21日" });
		resource.add(new String[] { "LVAV2MWB4DC022823", "8月21日" });
		resource.add(new String[] { "LVAV2MWBXDC022812", "8月21日" });
		resource.add(new String[] { "LVAV2MWB2DC022822", "8月21日" });
		resource.add(new String[] { "LVAV2MBB1DC021814", "8月20日" });
		resource.add(new String[] { "LVAV2MVB2DC019520", "8月20日" });
		resource.add(new String[] { "LVAV2MBB3DC022589", "8月20日" });
		resource.add(new String[] { "LVAV2MBB1DC022588", "8月20日" });
		resource.add(new String[] { "LVAV2MBBXDC022587", "8月20日" });
		resource.add(new String[] { "LVAV2MBB6DC022585", "8月20日" });
		resource.add(new String[] { "LVAV2MBB8DC020501", "8月20日" });
		resource.add(new String[] { "LVAV2MBB8DC022586", "8月20日" });
		resource.add(new String[] { "LVAV2MBBXDC022590", "8月20日" });
		resource.add(new String[] { "LVAV2MBB5DC022304", "8月18日" });
		resource.add(new String[] { "LVAV2MBB5DC022321", "8月18日" });
		resource.add(new String[] { "LVAV2MBB8DC022362", "8月18日" });
		resource.add(new String[] { "LVAV2MBB0DC022307", "8月18日" });
		resource.add(new String[] { "LVAV2MBB3DC022303", "8月18日" });
		resource.add(new String[] { "LVAV2MWB7DC022010", "8月18日" });
		resource.add(new String[] { "LVAV2MBB3DC022317", "8月18日" });
		resource.add(new String[] { "LVAV2MBB4DC022312", "8月18日" });
		resource.add(new String[] { "LVAV2MBBXDC022315", "8月18日" });
		resource.add(new String[] { "LVAV2MBB7DC022305", "8月18日" });
		resource.add(new String[] { "LVAV2MBB2DC022308", "8月18日" });
		resource.add(new String[] { "LVAV2MBB9DC022306", "8月18日" });
		resource.add(new String[] { "LVAV2MBB0DC022310", "8月18日" });
		resource.add(new String[] { "LVAV2MBB2DC022311", "8月18日" });
		resource.add(new String[] { "LVAV2MWB0DC022429", "8月12日" });
		resource.add(new String[] { "LVAV2MBB9DC021995", "8月15日" });
		resource.add(new String[] { "LVAV2MBB7DC021509", "8月7日" });
		resource.add(new String[] { "LVAV2MBBXDC020144", "8月7日" });
		resource.add(new String[] { "LVAV2MBB5DC020827", "8月7日" });
		resource.add(new String[] { "LVAV2MBB4DC021970", "8月8日" });
		resource.add(new String[] { "LVAV2MWBXDC019621", "8月11日" });
		resource.add(new String[] { "LVAV2MWBXDC021322", "8月21日" });
		resource.add(new String[] { "LVAV2MWB8DC021321", "8月21日" });
		resource.add(new String[] { "LVAV2MBB1DC021179", "8月21日" });
		resource.add(new String[] { "LVAV2MWB9DC021599", "8月8日" });
		resource.add(new String[] { "LVAV2MWB1DC021600", "8月8日" });
		resource.add(new String[] { "LVAV2MWB2DC021315", "8月16日" });
		resource.add(new String[] { "LVAV2MWB5DC021177", "8月16日" });
		resource.add(new String[] { "LVAV2MBB4DC021595", "8月6日" });
		resource.add(new String[] { "LVAV2MBB5DC021184", "8月6日" });
		resource.add(new String[] { "LVAV2MWB3DC021596", "8月6日" });
		resource.add(new String[] { "LVAV2MWB3DC021341", "8月6日" });
		resource.add(new String[] { "LVAV2MWBXDC021367", "8月6日" });
		resource.add(new String[] { "LVAV2MBB5DC021556", "8月6日" });
		resource.add(new String[] { "LVAV2MWB7DC021830", "8月4日" });
		resource.add(new String[] { "LVAV2MWB1DC021001", "8月4日" });
		resource.add(new String[] { "LVAV2MWB8DC021898", "8月4日" });
		resource.add(new String[] { "LVAV2MWB8DC021870", "8月4日" });
		resource.add(new String[] { "LVAV2MWB0DC021832", "8月4日" });
		resource.add(new String[] { "LVAV2MWB1DC021869", "8月4日" });
		resource.add(new String[] { "LVAV2MWBXDC011969", "8月4日" });
		resource.add(new String[] { "LVAV2MBB8DC021518", "8月2日" });
		resource.add(new String[] { "LVAV2MBB3DC021569", "8月2日" });
		resource.add(new String[] { "LVAV2MBB7DC021588", "8月2日" });
		resource.add(new String[] { "LVAV2MBB0DC021531", "8月2日" });
		resource.add(new String[] { "LVAV2MBB0DC021514", "8月2日" });
		resource.add(new String[] { "LVAV2MBB4DC021581", "8月2日" });
		resource.add(new String[] { "LVAV2MBB4DC021497", "8月2日" });
		resource.add(new String[] { "LVAV2MBB8DC021602", "8月2日" });
		resource.add(new String[] { "LVAV2MBB6DC021789", "8月6日" });
		resource.add(new String[] { "LVAV2MBB4DC021788", "8月6日" });
		resource.add(new String[] { "LVAV2MWB0DC021667", "8月6日" });
		resource.add(new String[] { "LVAV2MBB0DC021724", "8月6日" });
		resource.add(new String[] { "LVAV2MWB5DC021714", "8月6日" });
		resource.add(new String[] { "LVAV2MBBXDC021603", "8月6日" });
		resource.add(new String[] { "LVAV2MBB3DC021474", "8月6日" });
		resource.add(new String[] { "LVAV2MBB0DC021738", "8月6日" });
		resource.add(new String[] { "LVAV2MBB4DC021628", "8月6日" });
		resource.add(new String[] { "LVAV2MBB4DC020656", "8月6日" });
		resource.add(new String[] { "LVAV2MBB6DC021744", "8月11日" });
		resource.add(new String[] { "LVAV2MBB6DC021761", "8月14日" });
		resource.add(new String[] { "LVAV2MWBXDC021742", "8月14日" });
		resource.add(new String[] { "LVAV2MBB5DC021928", "8月14日" });
		resource.add(new String[] { "LVAV2MBB6DC021775", "8月14日" });
		resource.add(new String[] { "LVAV2MBB2DC021739", "8月14日" });
		resource.add(new String[] { "LVAV2MBB1DC021800", "8月14日" });
		resource.add(new String[] { "LVAV2MBB9DC021916", "8月14日" });
		resource.add(new String[] { "LVAV2MBB7DC020022", "8月14日" });
		resource.add(new String[] { "LVAV2MWB5DC021745", "8月14日" });
		resource.add(new String[] { "LVAV2MBB5DC020312", "7月31日" });
		resource.add(new String[] { "LVAV2MBB7DC020215", "7月31日" });
		resource.add(new String[] { "LVAV2MBB7DC021915", "8月3日" });
		resource.add(new String[] { "LVAV2MWB8DC021741", "8月3日" });
		resource.add(new String[] { "LVAV2MWB1DC021855", "8月3日" });
		resource.add(new String[] { "LVAV2MBB6DC020397", "7月31日" });
		resource.add(new String[] { "LVAV2MBB3DC020213", "7月31日" });
		resource.add(new String[] { "LVAV2MBB9DC021513", "7月31日" });
		resource.add(new String[] { "LVAV2MBB3DC020776", "7月31日" });
		resource.add(new String[] { "LVAV2MBB7DC021512", "7月31日" });
		resource.add(new String[] { "LVAV2MBB3DC020146", "7月31日" });
		resource.add(new String[] { "LVAV2MBB1DC020145", "7月31日" });
		resource.add(new String[] { "LVAV2MBB9DC020832", "7月31日" });
		resource.add(new String[] { "LVAV2MBB9DC020829", "7月31日" });
		resource.add(new String[] { "LVAV2MBB7DC020828", "7月31日" });
		resource.add(new String[] { "LVAV2MBB0DC021836", "8月3日" });
		resource.add(new String[] { "LVAV2MBB4DC021838", "8月3日" });
		resource.add(new String[] { "LVAV2MBB2DC021837", "8月3日" });
		resource.add(new String[] { "LVAV2MBB4DC021841", "8月6日" });
		resource.add(new String[] { "LVAV2MBB9DC021933", "8月6日" });
		resource.add(new String[] { "LVAV2MBB5DC021914", "8月6日" });
		resource.add(new String[] { "LVAV2MWB1DC021712", "8月4日" });
		resource.add(new String[] { "LVAV2MWB9DC021795", "8月4日" });
		resource.add(new String[] { "LVAV2MWB8DC021643", "8月4日" });
		resource.add(new String[] { "LVAV2MBB5DC020181", "8月4日" });
		resource.add(new String[] { "LVAV2MBB0DC021397", "8月3日" });
		resource.add(new String[] { "LVAV2MBB4DC021371", "8月3日" });
		resource.add(new String[] { "LVAV2MBB5DC021394", "8月3日" });
		resource.add(new String[] { "LVAV2MBB7DC021395", "8月3日" });
		resource.add(new String[] { "LVAV2MBB0DC021335", "8月3日" });
		resource.add(new String[] { "LVAV2MBB3DC020907", "8月3日" });
		resource.add(new String[] { "LVAV2MBB1DC020906", "8月3日" });
		resource.add(new String[] { "LVAV2MBB9DC021396", "8月3日" });
		resource.add(new String[] { "LVAV2MWBXDC021658", "8月2日" });
		resource.add(new String[] { "LVAV2MWB7DC021648", "8月2日" });
		resource.add(new String[] { "LVAV2MWB6DC021320", "8月2日" });
		resource.add(new String[] { "LVAV2MWB9DC021845", "8月2日" });
		resource.add(new String[] { "LVAV2MWB6DC021818", "8月2日" });
		resource.add(new String[] { "LVAV2MWBXDC020963", "8月2日" });
		resource.add(new String[] { "LVAV2MWB1DC021659", "8月2日" });
		resource.add(new String[] { "LVAV2MWB8DC021318", "8月2日" });
		resource.add(new String[] { "LVAV2MWB7DC021844", "8月2日" });
		resource.add(new String[] { "LVAV2MWB3DC021842", "8月2日" });
		resource.add(new String[] { "LVAV2MWBXDC021319", "8月2日" });
		resource.add(new String[] { "LVAV2MWBXDC021207", "8月2日" });
		resource.add(new String[] { "LVAV2MWB9DC021649", "8月2日" });
		resource.add(new String[] { "LVAV2MWBXDC021854", "8月2日" });
		resource.add(new String[] { "LVAV2MWB5DC021843", "8月2日" });
		resource.add(new String[] { "LVAV2MWB8DC021660", "8月2日" });
		resource.add(new String[] { "LVAV2MAB3DC021749", "7月31日" });
		resource.add(new String[] { "LVAV2MAB5DC021607", "7月31日" });
		resource.add(new String[] { "LVAV2MBB5DC021279", "7月28日" });
		resource.add(new String[] { "LVAV2MBB7DC021672", "7月28日" });
		resource.add(new String[] { "LVAV2MBBXDC021665", "7月28日" });
		resource.add(new String[] { "LVAV2MBB8DC021616", "7月28日" });
		resource.add(new String[] { "LVAV2MBB1DC021702", "7月28日" });
		resource.add(new String[] { "LVAV2MBB0DC021674", "7月28日" });
		resource.add(new String[] { "LVAV2MBB5DC021718", "7月28日" });
		resource.add(new String[] { "LVAV2MBB3DC021670", "7月28日" });
		resource.add(new String[] { "LVAV2MBBXDC021701", "7月28日" });
		resource.add(new String[] { "LVAV2MBB4DC021077", "7月28日" });
		resource.add(new String[] { "LVAV2MBB0DC021092", "7月24日" });
		resource.add(new String[] { "LVAV2MBB2DC021031", "7月20日" });
		resource.add(new String[] { "LVAV2MBB7DC020859", "7月20日" });
		resource.add(new String[] { "LVAV2MBB5DC020780", "7月20日" });
		resource.add(new String[] { "LVAV2MBB3DC020860", "7月20日" });
		resource.add(new String[] { "LVAV2MBB5DC020861", "7月20日" });
		resource.add(new String[] { "LVAV2MBB7DC020618", "7月20日" });
		resource.add(new String[] { "LVAV2MBB8DC019865", "7月20日" });
		resource.add(new String[] { "LVAV2MBB5DC020617", "7月20日" });
		resource.add(new String[] { "LVAV2MBB3DC020616", "7月20日" });
		resource.add(new String[] { "LVAV2MBB2DC020803", "7月21日" });
		resource.add(new String[] { "LVAV2MBB4DC020916", "7月21日" });
		resource.add(new String[] { "LVAV2MBBXDC020015", "7月21日" });
		resource.add(new String[] { "LVAV2MBB4DC020978", "7月21日" });
		resource.add(new String[] { "LVAV2MBB2DC020719", "7月21日" });
		resource.add(new String[] { "LVAV2MBB7DC020909", "7月21日" });
		resource.add(new String[] { "LVAV2MBB9DC020913", "7月21日" });
		resource.add(new String[] { "LVAV2MBB2DC020929", "7月21日" });
		resource.add(new String[] { "LVAV2MBB7DC020036", "7月19日" });
		resource.add(new String[] { "LVAV2MBB6DC020142", "7月19日" });
		resource.add(new String[] { "LVAV2MBB7DC020506", "7月19日" });
		resource.add(new String[] { "LVAV2MBBXDC020791", "7月19日" });
		resource.add(new String[] { "LVAV2MBB8DC020790", "7月19日" });
		resource.add(new String[] { "LVAV2MBB3DC020034", "7月19日" });
		resource.add(new String[] { "LVAV2MBB1DC020744", "7月19日" });
		resource.add(new String[] { "LVAV2MBB3DC020745", "7月19日" });
		resource.add(new String[] { "LVAV2MAB9DC020301", "7月18日" });
		resource.add(new String[] { "LVAV2MAB5DC020246", "7月18日" });
		resource.add(new String[] { "LVAV2MAB3DC020648", "7月18日" });
		resource.add(new String[] { "LVAV2MAB7DC020328", "7月18日" });
		resource.add(new String[] { "LVAV2MAB1DC020647", "7月18日" });
		resource.add(new String[] { "LVAV2MAB3DC020245", "7月18日" });
		resource.add(new String[] { "LVAV2MAB5DC020327", "7月18日" });
		resource.add(new String[] { "LVAV2MAB5DC020649", "7月18日" });
		resource.add(new String[] { "LVAV2MAB1DC020650", "7月18日" });
		resource.add(new String[] { "LVAV2MAB1DC020244", "7月18日" });
		resource.add(new String[] { "LVAV2MBBXDC015915", "7月21日" });
		resource.add(new String[] { "LVAV2MBBXDC020564", "7月21日" });
		resource.add(new String[] { "LVAV2MBB0DC018774", "7月21日" });
		resource.add(new String[] { "LVAV2MBB8DC018778", "7月21日" });
		resource.add(new String[] { "LVAV2MBB7DC019212", "7月21日" });
		resource.add(new String[] { "LVAV2MBBXDC018779", "7月21日" });
		resource.add(new String[] { "LVAV2MBB0DC018791", "7月21日" });
		resource.add(new String[] { "LVAV2MBB4DC018745", "7月21日" });
		resource.add(new String[] { "LVAV2MBB5DC020486", "7月16日" });
		resource.add(new String[] { "LVAV2MBB1DC020484", "7月16日" });
		resource.add(new String[] { "LVAV2MBB4DC020494", "7月16日" });
		resource.add(new String[] { "LVAV2MBB9DC020488", "7月16日" });
		resource.add(new String[] { "LVAV2MBB3DC020552", "7月16日" });
		resource.add(new String[] { "LVAV2MBB4DC020477", "7月16日" });
		resource.add(new String[] { "LVAV2MBB2DC020476", "7月16日" });
		resource.add(new String[] { "LVAV2MBB0DC020475", "7月16日" });
		resource.add(new String[] { "LVAV2MBB6DC020352", "7月15日" });
		resource.add(new String[] { "LVAV2MBB0DC020461", "7月15日" });
		resource.add(new String[] { "LVAV2MBB2DC020462", "7月15日" });
		resource.add(new String[] { "LVAV2MBB3DC020261", "7月15日" });
		resource.add(new String[] { "LVAV2MBB5DC020262", "7月15日" });
		resource.add(new String[] { "LVAV2MBB2DC020350", "7月15日" });
		resource.add(new String[] { "LVAV2MBB4DC020351", "7月15日" });
		resource.add(new String[] { "LVAV2MBB8DC020353", "7月15日" });
		resource.add(new String[] { "LVAV2MBB9DC020507", "7月15日" });
		resource.add(new String[] { "LVAV2MBB0DC020377", "7月15日" });
		resource.add(new String[] { "LVAV2MBB5DC020343", "7月15日" });
		resource.add(new String[] { "LVAV2MBB8DC020370", "7月15日" });
		resource.add(new String[] { "LVAV2MBB7DC020344", "7月15日" });
		resource.add(new String[] { "LVAV2MBB7DC020411", "7月15日" });
		resource.add(new String[] { "LVAV2MBB3DC020454", "7月15日" });
		resource.add(new String[] { "LVAV2MBB8DC020191", "7月15日" });
		resource.add(new String[] { "LVAV2MBB5DC018351", "7月16日" });
		resource.add(new String[] { "LVAV2MAB0DC019926", "7月16日" });
		resource.add(new String[] { "LVAV2MBB1DC019917", "7月16日" });
		resource.add(new String[] { "LVAV2MAB9DC019925", "7月16日" });
		resource.add(new String[] { "LVAV2MBB8DC020112", "7月16日" });
		resource.add(new String[] { "LVAV2MBBXDC020077", "7月16日" });
		resource.add(new String[] { "LVAV2MBB3DC019918", "7月16日" });
		resource.add(new String[] { "LVAV2MBB9DC018420", "7月16日" });
		resource.add(new String[] { "LVAV2MBB1DC018430", "7月16日" });
		resource.add(new String[] { "LVAV2MBB2DC020283", "7月16日" });
		resource.add(new String[] { "LVAV2MBB4DC020138", "7月16日" });
		resource.add(new String[] { "LVAV2MBB4DC020303", "7月16日" });
		resource.add(new String[] { "LVAV2MBB3DC020373", "7月16日" });
		resource.add(new String[] { "LVAV2MBB7DC020439", "7月16日" });
		resource.add(new String[] { "LVAV2MBB9DC020295", "7月16日" });
		resource.add(new String[] { "LVAV2MBB3DC020440", "7月16日" });
		resource.add(new String[] { "LVAV2MBB7DC020103", "7月16日" });
		resource.add(new String[] { "LVAV2MBB0DC019858", "7月14日" });
		resource.add(new String[] { "LVAV2MBB9DC020443", "7月14日" });
		resource.add(new String[] { "LVAV2MBB2DC019859", "7月14日" });
		resource.add(new String[] { "LVAV2MBB5DC019970", "7月14日" });
		resource.add(new String[] { "LVAV2MBB6DC019959", "7月14日" });
		resource.add(new String[] { "LVAV2MBB5DC020374", "7月14日" });
		resource.add(new String[] { "LVAV2MBB7DC020442", "7月14日" });
		resource.add(new String[] { "LVAV2MBB5DC020441", "7月14日" });
		resource.add(new String[] { "LVAV2MBB4DC020155", "7月14日" });
		resource.add(new String[] { "LVAV2MBB0DC020153", "7月14日" });
		resource.add(new String[] { "LVAV2MBB9DC020152", "7月14日" });
		resource.add(new String[] { "LVAV2MBB7DC020151", "7月14日" });
		resource.add(new String[] { "LVAV2MBB4DC020432", "7月14日" });
		resource.add(new String[] { "LVAV2MBBXDC019866", "7月14日" });
		resource.add(new String[] { "LVAV2MBB2DC020431", "7月14日" });
		resource.add(new String[] { "LVAV2MBB6DC020156", "7月14日" });
		resource.add(new String[] { "LVAV2MBB8DC020367", "7月11日" });
		resource.add(new String[] { "LVAV2MBB4DC020415", "7月11日" });
		resource.add(new String[] { "LVAV2MBB9DC020037", "7月11日" });
		resource.add(new String[] { "LVAV2MBB0DC020038", "7月11日" });
		resource.add(new String[] { "LVAV2MBB2DC020039", "7月11日" });
		resource.add(new String[] { "LVAV2MBBXDC020368", "7月11日" });
		resource.add(new String[] { "LVAV2MBB1DC020369", "7月11日" });
		resource.add(new String[] { "LVAV2MBB1DC020128", "7月11日" });
		resource.add(new String[] { "LVAV2MBB9DC020264", "7月13日" });
		resource.add(new String[] { "LVAV2MBB2DC019635", "7月13日" });
		resource.add(new String[] { "LVAV2MBB0DC020265", "7月13日" });
		resource.add(new String[] { "LVAV2MBB3DC019644", "7月13日" });
		resource.add(new String[] { "LVAV2MBB8DC019610", "7月13日" });
		resource.add(new String[] { "LVAV2MBBXDC020189", "7月13日" });
		resource.add(new String[] { "LVAV2MBB8DC019798", "7月11日" });
		resource.add(new String[] { "LVAV2MBBXDC020032", "7月11日" });
		resource.add(new String[] { "LVAV2MBB1DC020033", "7月11日" });
		resource.add(new String[] { "LVAV2MBB5DC020035", "7月11日" });
		resource.add(new String[] { "LVAV2MBB7DC019775", "7月11日" });
		resource.add(new String[] { "LVAV2MBB6DC019797", "7月11日" });
		resource.add(new String[] { "LVAV2MBB5DC019791", "7月11日" });
		resource.add(new String[] { "LVAV2MBB4DC019796", "7月11日" });
		resource.add(new String[] { "LVAV2MBBXDC019799", "7月11日" });
		resource.add(new String[] { "LVAV2MBBXDC017843", "7月10日" });
		resource.add(new String[] { "LVAV2MBB6DC018519", "7月6日" });
		resource.add(new String[] { "LVAV2MWB8DC018256", "7月9日" });
		resource.add(new String[] { "LVAV2MBB0DC018239", "7月9日" });
		resource.add(new String[] { "LVAV2MWB2DC018253", "7月7日" });
		resource.add(new String[] { "LVAV2MWB9DC018086", "7月7日" });
		resource.add(new String[] { "LVAV2MBB5DC017930", "7月7日" });
		resource.add(new String[] { "LVAV2MBB9DC017929", "7月7日" });
		resource.add(new String[] { "LVAV2MBB7DC017962", "7月7日" });
		resource.add(new String[] { "LVAV2MBBXDC017812", "7月7日" });
		resource.add(new String[] { "LVAV2MWB4DC018139", "7月7日" });
		resource.add(new String[] { "LVAV2MWB0DC018140", "7月7日" });
		resource.add(new String[] { "LVAV2MVB4DC017946", "7月7日" });
		resource.add(new String[] { "LVAV2MWB1DC019538", "7月7日" });
		resource.add(new String[] { "LVAV2MBB1DC019027", "7月5日" });
		return resource;
	}
}

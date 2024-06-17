package dtbase;

public final class statement {
    public static final String get_data_contracts = "select idContracts, " +
            "typeCargo, " +
            "weightCargo, " +
            "contractDate, " +
            "contractStatus, " +
            "price, " +
            "statusInsurance, " +
            "nameClientCompany, " +
            "typeTrain, " +
            "(select nameStation from stations where contractstations.dispatchStation=stations.idstations), " +
            "(select nameStation from stations where contractstations.arrivalStation=stations.idstations) " +
            "from contracts " +
            "inner join payments on contracts.idpayment = payments.idpayment " +
            "inner join insurance on contracts.idinsurance = insurance.idinsurance " +
            "inner join clients on contracts.idclients = clients.idclients " +
            "inner join trains on contracts.idtrains = trains.idtrains " +
            "join contractstations on contracts.contractstations = contractstations.idcontractstations";
    public static final String sort_payments = "select * from payments order by price desc";
    public static final String union_weight = "select idContracts,typeCargo,weightCargo " +
            "from contracts " +
            "where weightCargo = (select max(weightCargo) from contracts) " +
            "union " +
            "select idContracts,typeCargo,weightCargo " +
            "from contracts " +
            "where weightCargo = (select min(weightCargo) from contracts)";
    public static final String count_contract = "select nameClientCompany, count(*)  as numContracts " +
            "from contracts inner join clients " +
            "on contracts.idclients = clients.idclients " +
            "group by nameClientCompany";
    public static final String trains_not_active = "select typeTrain " +
            "from contracts as c1 left join trains " +
            "on c1.idtrains = trains.idtrains " +
            "where contractDate not between current_date - interval 1 month and current_date";
    public static final String contract_month = "select count(*) as numContracts from contracts " +
    "where extract(month from contractDate) = extract(month from current_date)";
    public static final String first_station = "select * from stations " +
            "where nameStation like ?%";
    public static final String bigger_tariffs = "select nameTariff,price " +
            "from tariffs " +
            "where idtariffs = any (select idtariffs from tariffs where price > ?)";
    public static final String companies_contr = "select distinct nameClientCompany,price " +
    "from contracts as c1 inner join payments as p1 " +
    "on c1.idpayment = p1.idpayment " +
    "inner join clients as cl1 " +
    "on c1.idclients = cl1.idclients " +
    "where p1.price = (select max(p2.price) " +
    "from contracts as c2 inner join payments as p2 " +
    "on c2.idpayment = p2.idpayment " +
    "inner join clients as cl2 " +
    "on c2.idclients = cl2.idclients " +
    "where cl1.idclients = cl2.idclients)";



}

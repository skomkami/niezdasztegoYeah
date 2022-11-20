package pl.edu.agh.server;

import pl.edu.agh.model.IpnSourceModel;
import pl.edu.agh.service.clarin.ClarinService;

public class ConsoleTestApp {

    public static void main(String[] args) throws Exception {
        new ClarinService().calculateTokensForSource(new IpnSourceModel(
                "test.pl",
                "Hitler mieszkał w nazistowskich Niemczech, a dodatkowo Polskie Towarzystwo Łowieckie"
        ));
    }
}





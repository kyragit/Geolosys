package com.oitsjustjose.geolosys.client.patchouli.processors;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.concurrent.CompletableFuture;

import vazkii.patchouli.api.IComponentProcessor;
import vazkii.patchouli.api.IVariable;
import vazkii.patchouli.api.IVariableProvider;

public class PatronProcessor implements IComponentProcessor {

    private static IVariable patrons;

    public static void fetchPatrons() {
        CompletableFuture.runAsync(() -> {
            try {
                URL url = new URL("https://patrons.geolosys.com/");
                BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));

                String line;
                StringBuilder allData = new StringBuilder("$(l)A huge thank you to: $()$(br2)");
                while ((line = in.readLine()) != null) {
                    if (!line.isEmpty()) {
                        allData.append(line);
                    }
                }
                in.close();

                patrons = IVariable.wrap(allData.toString());
            } catch (IOException e) {
                patrons = IVariable.wrap("Failed to grab patrons from API");
            }
        });
    }

    @Override
    public void setup(IVariableProvider variables) {
        // No setup required, fetchPatrons is ran from Geolosys main on startup
    }

    @Override
    public IVariable process(String key) {
        if (key.equals("patrons")) {
            return patrons;
        }
        return null;
    }
}

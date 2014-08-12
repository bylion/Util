private void checkMaintenance() {
        try {
            Map<String, Object> jsonobject = jsonUtils.parseFlatMap(calljson(LocaleUtils.getLocale()));
            if (null != jsonobject && null != jsonobject.get("maintenanceMess")) {
                String mess = jsonobject.get("maintenanceMess").toString();
                if (mess.isEmpty()) {
                    setMaintenanceTimeStr(null);
                } else {
                    setMaintenanceTimeStr(mess);
                }
            }
        } catch (IOException e) {
            LOG.error("Can't connect to a3 and call maintenanceMess webservice", e);
        } catch (PlutoException e) {
            LOG.error("Can't parse Object to Json format", e);
        }
    }

    private String calljson(Locale l) throws IOException {
        String hostStr = request.getServerName();
        String url = "http://" + hostStr + "/a3/maintenanceMess.action?country=" + l.getCountry();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

    private String calljson(Locale l) throws IOException {
        String hostStr = request.getServerName();
        String url = "http://" + hostStr + "/a3/maintenanceMess.action?country=" + l.getCountry();
        URL obj = new URL(url);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();
        con.setRequestProperty("Content-Type", "text/plain; charset=utf-8");
        // optional default is GET
        con.setRequestMethod("GET");
        //add request header
        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response.toString();
    }

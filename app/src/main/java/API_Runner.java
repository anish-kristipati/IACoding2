import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.io.FileWriter;
import org.json.JSONObject;

public class API_Runner {
    private final String APIKEY = "AIzaSyAN3D6OaTZLsw-4cYDVh3ybpuznBfKA1Ow";
    String root = "https://www.googleapis.com/civicinfo/v2";
    URL path;
    BufferedReader in;
    User user;
    String encodedAddress;
    JSONObject x;
    boolean def = false;
    String[] choices = { "administrativeArea1",
            "administrativeArea2",
            "country",
            "international",
            "locality",
            "regional",
            "special",
            "subLocality1",
            "subLocality2" };
    String[] officeValues = { "deputyHeadOfGovernment",
            "executiveCouncil",
            "governmentOfficer",
            "headOfGovernment",
            "headOfState",
            "highestCourtJudge",
            "judge",
            "legislatorLowerBody",
            "legislatorUpperBody",
            "schoolBoard",
            "specialPurposeOfficer" };
    String[] ocdID;

    // Default Constructor
    public API_Runner(User user) throws IOException {
        encodeAddress(user.getAddress());
        if (user.getAddress().equals("1263 Pacific Ave. Kansas City KS")) {
            def = true;
        }
        storeOCD_ID();
        x.clear();
    }

    private void encodeAddress(String address) throws UnsupportedEncodingException {
        encodedAddress = URLEncoder.encode(address, "UTF-8");
    }

    // Views all elections in database (for debugging purposes)
    public String viewElections() throws IOException {
        path = new URL(root + "/elections?key=" + APIKEY);
        setBufferedReader();
        x = requestToJsonObject();
        return x.toString(2);
    }

    public String voterInfo() throws IOException {
        path = new URL(
                root + "/voterinfo?address=" + encodedAddress + "&key=" + APIKEY);

        setBufferedReader();
        x = requestToJsonObject();
        return x.toString(2);
    }

    public String voterInfo(boolean def) throws IOException {
        if (def) {
            path = new URL(
                    root + "/voterinfo?address=" + encodedAddress + "&electionId=2000"
                            + "&key=" + APIKEY);
            setBufferedReader();
            x = requestToJsonObject();
        }
        return x.toString(2);
    }

    public String repInfo() throws IOException {
        path = new URL(root + "/representatives?address=" + encodedAddress + "&key=" + APIKEY);
        setBufferedReader();
        x = requestToJsonObject();

        return x.toString(2);
    }

    public String repInfo(int modx1, int modx2, boolean levels, boolean roles) throws IOException {
        if (roles && levels) {
            path = new URL(root + "/representatives?address=" + encodedAddress + "&levels=" + choices[modx1] + "&roles="
                    + officeValues[modx2] + "&key=" + APIKEY);
        } else if (levels) {
            path = new URL(root + "/representatives?address=" + encodedAddress + "&levels=" + choices[modx1] + "&key="
                    + APIKEY);
        } else if (roles) {
            path = new URL(root + "/representatives?address=" + encodedAddress + "&roles=" + officeValues[modx2]
                    + "&key=" + APIKEY);
        } else {
            path = new URL(root + "/representatives?address=" + encodedAddress + "&key=" + APIKEY);
        }
        setBufferedReader();
        x = requestToJsonObject();
        return x.toString(2);
    }

    private void setBufferedReader() throws IOException {
        in = new BufferedReader(new InputStreamReader(path.openStream()));
    }

    public JSONObject requestToJsonObject() throws IOException {
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = in.readLine()) != null) {
            sb.append(line);
        }
        JSONObject json = new JSONObject(sb.toString());
        return json;
    }

    public void saveToFile(JSONObject json) throws IOException {
        FileWriter file = new FileWriter("test.json");
        BufferedWriter bw = new BufferedWriter(file);
        bw.write(json.toString(2));
        bw.flush();
    }

    private void storeOCD_ID() throws IOException {
        repInfo();
        JSONObject json2 = x.getJSONObject("divisions");
        ocdID = JSONObject.getNames(json2);

    }
    public String[] getOCD(){
        return ocdID;
    }
}

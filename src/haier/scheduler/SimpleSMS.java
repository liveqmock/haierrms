package haier.scheduler;

import com.ccb.dao.EVTMAININFO;
import gateway.sms.HaierSms;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: zhanrui
 * Date: 13-6-9
 * Time: ����4:42
 * To change this template use File | Settings | File Templates.
 */
public class SimpleSMS {
    private Logger logger = Logger.getLogger(this.getClass());

    public void start() {

        String smsStr = "���絽������ף����!ףԸ����ҵ������һ���������ͥ�����" +
                "һ����ܰ����������Ҷһ��Ʈ�ݣ��������ۻ�һ�����ԣ�������Ŵ" +
                "��һ��ճ������ ���Ƚ��գ��������Ρ����ϴǮ���ά������" +
                "���������������𣡺�������˾ף������ڿ���!";

//        String[] phones = getPhones();
        String phones[] = {"13905320231", "18661611969"};

        for (String phone : phones) {
            boolean result = HaierSms.sendMessage(phone, smsStr);
            logger.info(phone + "���ͽ��:" + result);
        }
    }

    private String[] getPhonesFromDB() {
        List list = EVTMAININFO.find("", true);

        EVTMAININFO evt = null;
        for(int i=0; i<list.size(); i++){
             evt = (EVTMAININFO)list.get(i);
        }

        String phoneRecord = evt.getSms_list();

        return null;
    }

    private String[] getPhones() {
        String smsStr = "15160981701\n" +
                "13805918987\n" +
                "18669691878\n" +
                "15854497868\n" +
                "13666643668\n" +
                "13920975093\n" +
                "13752316065\n" +
                "13792876311\n" +
                "13969632711\n" +
                "13906488559\n" +
                "13527465608\n" +
                "13805699588\n" +
                "13956056052\n" +
                "15952799802\n" +
                "13903732276\n" +
                "15253232831\n" +
                "13915720673\n" +
                "18918758093\n" +
                "13500275559\n" +
                "18656018123\n" +
                "13641986824\n" +
                "15333600551\n" +
                "13506317272\n" +
                "18660518700\n" +
                "13911076617\n" +
                "13628459319\n" +
                "13560129683\n" +
                "15002871894\n" +
                "18905695889\n" +
                "13961827240\n" +
                "13825579980\n" +
                "13608988611\n" +
                "15953271987\n" +
                "15953271987\n" +
                "18653178867\n" +
                "13520273693\n" +
                "18669691878\n" +
                "13075566675\n" +
                "15253232831\n" +
                "13896155853\n" +
                "13552724218\n" +
                "18561361622\n" +
                "13903016536\n" +
                "13906428344\n" +
                "13853218060\n" +
                "15801569693\n" +
                "13691322235\n" +
                "13655550658\n" +
                "13361280335\n" +
                "13917371125\n" +
                "13356875886\n" +
                "15653651088\n" +
                "15653651058\n" +
                "13476004297\n" +
                "15913163961\n" +
                "13165097770\n" +
                "13606186620\n" +
                "13656120686\n" +
                "15588873588\n" +
                "18685131533\n" +
                "15263188409\n" +
                "13455258886\n" +
                "13953245671\n" +
                "13450269301\n" +
                "13462307427\n" +
                "13811058393\n" +
                "18769995053\n" +
                "15002857436\n" +
                "13964291551\n" +
                "15156017294\n" +
                "13052468040\n" +
                "13969666890\n" +
                "13478135133\n" +
                "13455245534\n" +
                "13601768236\n" +
                "18949832560\n" +
                "13548979105\n" +
                "13983013727\n" +
                "13839089171\n" +
                "15974146938\n" +
                "18053278993\n" +
                "13583256262\n" +
                "13339208888\n" +
                "13953203315\n" +
                "18053278769\n" +
                "13583277976\n" +
                "13868780715\n" +
                "13616152700\n" +
                "13730970979\n" +
                "18650308232\n" +
                "13834521394\n" +
                "18653824282\n" +
                "13589228802\n" +
                "13834853298\n" +
                "13872349989\n" +
                "13730929626\n" +
                "15835034100\n" +
                "13100511412\n" +
                "13623678352\n" +
                "13306459666\n" +
                "13840476357\n" +
                "13937197610\n" +
                "13637492013\n" +
                "13302828567\n" +
                "15053211091\n" +
                "13705325125\n" +
                "13702460107\n" +
                "15629008973\n" +
                "18684867628\n" +
                "13996245122\n" +
                "15232793634\n" +
                "15959047500\n" +
                "13776504070\n" +
                "13955198105\n" +
                "13631361108\n" +
                "13395323161\n" +
                "18766395660\n" +
                "18636922233\n" +
                "15092101688\n" +
                "13550225808\n" +
                "15866894583\n" +
                "15210664717\n" +
                "18615257912\n" +
                "13361368818\n" +
                "15605148316\n" +
                "15982640097\n" +
                "13628392469\n" +
                "13906426723\n" +
                "13752612158\n" +
                "18678908690\n" +
                "15244488867\n" +
                "13509963008\n" +
                "13276386077\n" +
                "13881887100\n" +
                "13970921862\n" +
                "18921333612\n" +
                "18760430533\n" +
                "13599785862\n" +
                "13679760754\n" +
                "18605178323\n" +
                "13210160927\n" +
                "13705329042\n" +
                "13705329042\n" +
                "15253232831\n" +
                "13918809561\n" +
                "15615882267\n" +
                "13241491984\n" +
                "13834578946\n" +
                "18906312820\n" +
                "15063866797\n" +
                "13864231159\n" +
                "18660202431\n" +
                "13553156669\n" +
                "13705325125\n" +
                "13210286781\n" +
                "15053191001\n" +
                "13105138556\n" +
                "18756078180\n" +
                "13902162420\n" +
                "13606855886\n" +
                "13971750241\n" +
                "13771557745\n" +
                "13668942905\n" +
                "13883237367\n" +
                "13806398790\n" +
                "18653824282\n" +
                "13883098593\n" +
                "13872938468\n" +
                "13628304275\n" +
                "13012420120\n" +
                "13466888032\n" +
                "18663973972\n" +
                "13592453832\n" +
                "15566402303\n" +
                "18653288833\n" +
                "13602192057\n" +
                "13853228713\n" +
                "13256856039\n" +
                "13075361218\n" +
                "15064290609\n" +
                "13896155853\n" +
                "13792876052\n" +
                "13632275799\n" +
                "15054208638\n" +
                "13688147421\n" +
                "13801441893\n" +
                "13646396258\n" +
                "13687623069\n" +
                "13969711992\n" +
                "15053738736\n" +
                "13793207680\n" +
                "13761306904\n" +
                "13361237399\n" +
                "13791936265\n" +
                "13941168515\n" +
                "13996281101\n" +
                "13969876285\n" +
                "15253232831\n" +
                "13705329042\n" +
                "13791818190\n" +
                "13581694927\n" +
                "13808911009\n" +
                "15905390532\n" +
                "13370920361\n" +
                "13370895775\n" +
                "13605326130\n" +
                "13017672167\n" +
                "13863926103\n" +
                "18919676818\n" +
                "15666665005\n" +
                "13853201161\n" +
                "13964291551\n" +
                "18721310880\n" +
                "18605178323\n" +
                "18661789136\n" +
                "13884975551\n" +
                "15954854610\n" +
                "13469817940\n" +
                "13512333900\n" +
                "13964200319\n" +
                "13564443540\n" +
                "13869822883\n" +
                "15953245304\n" +
                "13705510838\n" +
                "13817512319\n" +
                "13705325125\n" +
                "13520190601\n" +
                "15105365935\n" +
                "15225902874\n" +
                "18655187864\n" +
                "18949832560\n" +
                "15269219748\n" +
                "15866892939\n" +
                "15253232831\n" +
                "18765232629\n" +
                "13954283702\n" +
                "13501540259\n" +
                "13908613100\n" +
                "15866022088\n" +
                "18653851032\n" +
                "13780606998\n" +
                "15898871981\n" +
                "13606345198\n" +
                "13853294226\n" +
                "13866740540\n" +
                "13877114392\n" +
                "15069002802\n" +
                "13589273953\n" +
                "13808987727\n" +
                "15023015373\n" +
                "13520972511\n" +
                "18660856092\n" +
                "13735196662\n" +
                "13553013027\n" +
                "15251929932\n" +
                "15071199164\n" +
                "15806608382\n" +
                "13153149218\n" +
                "18669832211\n" +
                "13926064618\n" +
                "13589397978\n" +
                "18995777998\n" +
                "13210252320\n" +
                "13999812067\n" +
                "13954283702\n" +
                "13631338798\n" +
                "13478449479\n" +
                "15253232831\n" +
                "13705325125\n" +
                "13235311161\n" +
                "13626428712\n" +
                "15263030800\n" +
                "13210203175\n" +
                "13573810657\n" +
                "13453134305\n" +
                "13864212045\n" +
                "13625420236\n" +
                "18979169756\n" +
                "13761306904\n" +
                "13691573244\n" +
                "18773137926\n" +
                "15153191071\n" +
                "13356888893\n" +
                "13853294226\n" +
                "18660257693\n" +
                "15269219748\n" +
                "13969708256\n" +
                "15055113115\n" +
                "13803066457\n" +
                "15263188409\n" +
                "13884828370\n" +
                "15805354864\n" +
                "15969827548\n" +
                "13365508688\n" +
                "18723127089\n" +
                "15898871808\n" +
                "13853299612\n" +
                "18764641555\n" +
                "13668289810\n" +
                "18933391270\n" +
                "13698694826\n" +
                "13953245671\n" +
                "13864231159\n" +
                "13837148161\n" +
                "15921020233\n" +
                "13687622806\n" +
                "13589353370\n" +
                "13065035346\n" +
                "13628304275\n" +
                "13601019398\n" +
                "15166636238\n" +
                "13853299523\n" +
                "15253232806\n" +
                "15053191001\n" +
                "15908999311\n" +
                "13656482991\n" +
                "15864259095\n" +
                "15864259095\n" +
                "13608093992\n" +
                "13917338355\n" +
                "18623132820\n" +
                "13956004678\n" +
                "13601768236\n" +
                "15253232831\n" +
                "13567575298\n" +
                "13389061897\n" +
                "13954261760\n" +
                "13668865456\n" +
                "18660194098\n" +
                "15863112337\n" +
                "15810059616\n" +
                "13603587536\n" +
                "18202412288\n" +
                "13409209578\n" +
                "18933391270\n" +
                "13006363636\n" +
                "13706521573\n" +
                "13544486152\n" +
                "13574108211\n" +
                "13396483398\n" +
                "15910108075\n" +
                "15253232831\n" +
                "13853665059\n" +
                "18851571365\n" +
                "13583183382\n" +
                "13868721031\n" +
                "15215154679\n" +
                "15910108075\n" +
                "13576925380\n" +
                "13958836699\n" +
                "13962215399\n" +
                "18674850324\n" +
                "18953242756\n" +
                "13668860111\n" +
                "13854217187\n" +
                "15806553130\n" +
                "13515326855\n" +
                "13864875818\n" +
                "13805329655\n" +
                "13698698692\n" +
                "13854224897\n" +
                "18661622219\n" +
                "13969669068\n" +
                "15264260026\n" +
                "18669701719\n" +
                "13583290498\n" +
                "13869841978\n" +
                "15092295023\n" +
                "13969669029\n" +
                "13626396078\n" +
                "18653205599\n" +
                "13573832588\n" +
                "13370865887\n" +
                "18653250708\n" +
                "13705329978\n" +
                "13698689513\n" +
                "13793232324\n" +
                "13792879280\n" +
                "15264251539\n" +
                "13969670855\n" +
                "13869814601\n" +
                "13646485371\n" +
                "13061322585\n" +
                "18669858638\n" +
                "13869863859\n" +
                "15092182975\n" +
                "15020080817\n" +
                "13953210212\n" +
                "15265328689\n" +
                "13606348959\n" +
                "13964806620\n" +
                "15963201140\n" +
                "18661865623\n" +
                "13668898526\n" +
                "13165082676\n" +
                "15064216605\n" +
                "13583256943\n" +
                "13792435263\n" +
                "13730932800\n" +
                "18661628118\n" +
                "15966946387\n" +
                "13061307046\n" +
                "13553001267\n" +
                "13370838832\n" +
                "13791936265\n" +
                "15092031069\n" +
                "13963985716\n" +
                "15269219779\n" +
                "13606425476\n" +
                "13605326320\n" +
                "18953272278\n";
        return smsStr.split("\n");
    }

    public static void main(String... argv) {
        SimpleSMS sms = new SimpleSMS();
        sms.start();
    }
}
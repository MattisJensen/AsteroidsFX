package dk.sdu.mmmi.cbse.enemysystem;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;
import dk.sdu.mmmi.cbse.common.services.IGamePluginService;

public class EnemyPlugin implements IGamePluginService {
    private Entity enemy;

    @Override
    public void start(GameData gameData, World world) {
        enemy = createEnemyShip(gameData);
        world.addEntity(enemy);
    }

    private Entity createEnemyShip(GameData gameData) {
        Entity enemyShip = new Enemy();
        enemyShip.setPolygonCoordinates(9.10893440246582, 1.0004798720474355, 8.969651222229004, 1.8184452056884766, 8.830368518829346, 2.6364126205444336, 8.691085815429688, 3.454378128051758, 8.55180311203003, 4.272343635559082, 8.412520408630371, 5.090310096740723, 8.273237705230713, 5.908276081085205, 8.133955001831055, 6.726242542266846, 7.9946722984313965, 7.544209003448486, 7.855389595031738, 8.36217451095581, 7.71610689163208, 9.18014144897461, 7.576824188232422, 9.998106956481934, 7.437541484832764, 10.816072463989258, 7.2982587814331055, 11.634039878845215, 7.158976078033447, 12.452005386352539, 7.019693374633789, 13.269970893859863, 6.9083380699157715, 12.996485710144043, 6.811069965362549, 12.172466278076172, 6.713801860809326, 11.348447799682617, 6.6165337562561035, 10.524429321289062, 6.519265651702881, 9.700410842895508, 6.421997547149658, 8.876392364501953, 6.3247294425964355, 8.052372932434082, 6.227461338043213, 7.228353500366211, 6.130193710327148, 6.404335975646973, 6.032925128936768, 5.580316066741943, 5.783127307891846, 6.254077911376953, 5.52174711227417, 7.041572570800781, 5.260366439819336, 7.829068183898926, 4.998986005783081, 8.61656379699707, 4.737606048583984, 9.404057502746582, 4.47622537612915, 10.191553115844727, 4.2148449420928955, 10.979049682617188, 3.953464984893799, 11.766542434692383, 3.692084312438965, 12.554039001464844, 3.430704355239868, 13.341532707214355, 3.1693239212036133, 14.1290283203125, 2.9079432487487793, 14.916523933410645, 2.9319381713867188, 15.684624671936035, 3.297456741333008, 16.429519653320312, 3.662973642349243, 17.17441177368164, 4.0284905433654785, 17.91930389404297, 4.394007205963135, 18.664196014404297, 4.759525775909424, 19.409090042114258, 4.967423439025879, 20.155214309692383, 4.613595008850098, 20.90572738647461, 4.259764909744263, 21.656246185302734, 3.908055305480957, 22.374858856201172, 3.6566171646118164, 21.58413314819336, 3.4051778316497803, 20.793405532836914, 3.1537396907806396, 20.002681732177734, 2.902301549911499, 19.211956024169922, 2.650862216949463, 18.421228408813477, 2.4641242027282715, 18.756431579589844, 2.3049190044403076, 19.57075309753418, 2.1457130908966064, 20.38507843017578, 1.9865078926086426, 21.19940185546875, 1.8273026943206787, 22.013721466064453, 1.6680967807769775, 22.828048706054688, 1.5088915824890137, 23.642370223999023, 1.3496864140033722, 24.45669174194336, 1.1904804706573486, 25.27101707458496, 1.0312752835452557, 26.085338592529297, 1.4417667388916016, 26.74618148803711, 1.988501787185669, 27.370323181152344, 2.535234212875366, 27.994462966918945, 3.0819666385650635, 28.618602752685547, 3.628701686859131, 29.24274444580078, 4.175434112548828, 29.866884231567383, 4.722166538238525, 30.49102210998535, 4.969903469085693, 29.92047691345215, 5.1581902503967285, 29.112380981445312, 5.346476078033447, 28.30428695678711, 5.534761905670166, 27.49619483947754, 5.891700744628906, 28.05885124206543, 6.270256042480469, 28.797203063964844, 6.648811340332031, 29.535552978515625, 7.027368545532227, 30.273906707763672, 7.405925750732422, 31.01226234436035, 7.784479141235352, 31.750608444213867, 8.163036346435547, 32.48896408081055, 8.541593074798584, 33.227317810058594, 8.920146465301514, 33.96566390991211, 9.298852920532227, 33.963829040527344, 9.677706718444824, 33.22563552856445, 10.056564331054688, 32.487436294555664, 10.435420989990234, 31.749237060546875, 10.814274787902832, 31.011043548583984, 11.193131446838379, 30.272842407226562, 11.571989059448242, 29.53464126586914, 11.95084285736084, 28.79644775390625, 12.329699516296387, 28.05824851989746, 12.686803817749023, 27.496700286865234, 12.875260353088379, 28.30474853515625, 13.06371784210205, 29.11280632019043, 13.252175331115723, 29.92086410522461, 13.500165939331055, 30.4907169342041, 14.046640396118164, 29.86634635925293, 14.593114852905273, 29.241975784301758, 15.1395845413208, 28.617612838745117, 15.686059951782227, 27.993242263793945, 16.232534408569336, 27.368871688842773, 16.779004096984863, 26.744508743286133, 17.187219619750977, 26.082901000976562, 17.028186798095703, 25.268543243408203, 16.869155883789062, 24.454191207885742, 16.71012306213379, 23.63983154296875, 16.551090240478516, 22.825471878051758, 16.392059326171875, 22.011119842529297, 16.2330265045166, 21.196760177612305, 16.073994636535645, 20.382400512695312, 15.914962768554688, 19.568050384521484, 15.75593090057373, 18.753690719604492, 15.568944931030273, 18.42389488220215, 15.317325592041016, 19.214557647705078, 15.065704345703125, 20.00522804260254, 14.814083099365234, 20.7958984375, 14.562463760375977, 21.58656120300293, 14.31084156036377, 22.37723159790039, 13.958732604980469, 21.654035568237305, 13.604814529418945, 20.903568267822266, 13.250893592834473, 20.153093338012695, 13.461050987243652, 19.40711212158203, 13.826888084411621, 18.66238021850586, 14.192728042602539, 17.917644500732422, 14.558568954467773, 17.17290687561035, 14.924406051635742, 16.428176879882812, 15.29024600982666, 15.683440208435059, 15.313294410705566, 14.915392875671387, 15.05163288116455, 14.127996444702148, 14.789969444274902, 13.340593338012695, 14.528305053710938, 12.553190231323242, 14.266643524169922, 11.765792846679688, 14.004980087280273, 10.978389739990234, 13.743315696716309, 10.190985679626465, 13.481654167175293, 9.403589248657227, 13.219990730285645, 8.616185665130615, 12.95832633972168, 7.828782081604004, 12.696664810180664, 7.041385650634766, 12.435001373291016, 6.253982067108154, 12.184943199157715, 5.580318927764893, 12.087674140930176, 6.4043402671813965, 11.990406036376953, 7.2283616065979, 11.89313793182373, 8.052375316619873, 11.795869827270508, 8.876397132873535, 11.698601722717285, 9.700418472290039, 11.601333618164062, 10.524432182312012, 11.50406551361084, 11.348453521728516, 11.4067964553833, 12.17247486114502, 11.309529304504395, 12.996488571166992, 11.198175430297852, 13.26997184753418, 11.058892250061035, 12.452003479003906, 10.919610023498535, 11.634042739868164, 10.780326843261719, 10.816073417663574, 10.641043663024902, 9.9981050491333, 10.501760482788086, 9.180136680603027, 10.362478256225586, 8.362167835235596, 10.223196983337402, 7.544213771820068, 10.083913803100586, 6.726245403289795, 9.94463062286377, 5.908276557922363, 9.805347442626953, 5.09030818939209, 9.666064262390137, 4.272339582443237, 9.52678108215332, 3.4543709754943848, 9.387500762939453, 2.6364171504974365, 9.248217582702637, 1.818448543548584);
        enemyShip.setX(gameData.getDisplayHeight() / 3);
        enemyShip.setY(gameData.getDisplayWidth() / 3);
        return enemyShip;
    }

    @Override
    public void stop(GameData gameData, World world) {
        world.removeEntity(enemy);
    }
}

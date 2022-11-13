import Layout from '../components/Layout';
import PlanCalender from '../components/PlanCalender';
import PlanList from '../components/PlanList';
import Button from '../components/Button';
const Plan = () => {
  return (
    <Layout title="계획작성" hasTabBar>
      <div className="flex flex-col items-center justify-center h-screen mt-[-12em]">
        <PlanCalender />
        {/* position fixed 로 바꾸기 */}
        <PlanList />
        <div className="items-center max-w-lg bg-d-lighter fixed bottom-[4.5em] w-full px-11 pb-5 pt-5 flex justify-between">
          <Button text={'추가하기'} />
          <Button text={'저장하기'} />
        </div>
      </div>
    </Layout>
  );
};

export default Plan;

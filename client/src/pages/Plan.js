import Layout from '../components/Layout';
import PlanCalender from '../components/PlanCalender';
import PlanList from '../components/PlanList';
import Button from '../components/Button';
const Plan = () => {
  return (
    <Layout title="계획작성" hasTabBar>
      <PlanCalender />
      <PlanList />
      <div className="flex space-x-3 items-center justify-center">
        <Button text={'추가하기'} />
        <Button text={'저장하기'} />
      </div>
    </Layout>
  );
};

export default Plan;
